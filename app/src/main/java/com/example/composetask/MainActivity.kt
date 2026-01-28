package com.example.composetask

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.composetask.auth.AuthViewModel
import com.example.composetask.login.LoginScreen
import com.example.composetask.login.AuthTabs
import com.example.composetask.login.LoginState
import com.example.composetask.presentation.sign_in.GoogleAuthUiClient
import com.example.composetask.presentation.sign_in.HomeScreen
import com.example.composetask.presentation.sign_in.SignInViewModel
import com.example.composetask.presentation.sign_in.SignUpScreen
import com.example.composetask.presentation.sign_in.UserData
import com.example.composetask.presentation.sign_in.profile.ProfileScreen
import com.example.composetask.ui.theme.AppTheme
import com.example.composetask.ui.theme.ComposeTaskTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            var selectedTheme by rememberSaveable {
                mutableStateOf(AppTheme.BLOSSOM)
            }
            ComposeTaskTheme(
                theme = selectedTheme
            ) {
                AuthApp(
                    currentTheme = selectedTheme,
                    onThemeSelected = { selectedTheme = it }
                )

            }
        }


    }
}


@Composable
fun AuthApp(
    currentTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    NavHost(navController, startDestination = "auth") {
            navigation(startDestination = "sign_in", route = "auth") {
                composable("sign_in"){
                    Column {
                        AuthTabs(navController)

                        val authViewModel: AuthViewModel = hiltViewModel()
                        val state by authViewModel.loginState.collectAsStateWithLifecycle()
                        val user by authViewModel.currentUser.collectAsStateWithLifecycle()

                        val currentUser by authViewModel.currentUser.collectAsStateWithLifecycle()

                        LaunchedEffect(currentUser) {
                            if (currentUser != null) {
                                navController.navigate("profile") {
                                    popUpTo("auth") { inclusive = true }
                                }
                            }
                        }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = {result ->
                                if (result.resultCode == RESULT_OK) {
                                    authViewModel.handleGoogleSignInResult(result.data)
                                } else {
                                    authViewModel.onGoogleSignInCanceled()
                                }
                            }
                        )

                        LaunchedEffect(state) {
                            if (state is LoginState.Success) {
                                navController.navigate("profile") {
                                    popUpTo("sign_in") { inclusive = true }
                                }
                            }
                        }

                        LoginScreen(
                            state = state,
                            onSignInWithGoogleClick = {
                                scope.launch {
                                    val intentSender = authViewModel.getGoogleSignInIntent()


                                    if (intentSender != null) {
                                        launcher.launch(
                                            IntentSenderRequest.Builder(intentSender).build()
                                        )
                                    }
                                }

                            }, onEmailSignIn = { email, password ->
                                authViewModel.login(email, password)
                            },
                            currentTheme = currentTheme,
                            onThemeSelected = onThemeSelected,
                            onSignUpClick = {
                                navController.navigate("signup") {  popUpTo("auth") { inclusive = false }}
                            }
                        )
                    }


                }
                composable("signup") {
                    Column {
                        AuthTabs(navController)
                        SignUpScreen(navController)
                    }
                }


            }



        composable("home") {HomeScreen(navController)}
        composable("profile") {
            val authViewModel: AuthViewModel = hiltViewModel()
            val user by authViewModel.currentUser.collectAsStateWithLifecycle()

            ProfileScreen(
                userData = user?.let{
                    UserData(
                        userId = it.uid,
                        username = it.displayName,
                        profilePictureUrl = it.photoUrl?.toString()
                        )
                },
                onSignOut = {
                    scope.launch {
                        authViewModel.signOut()
                        navController.navigate("sign_in") { popUpTo("auth") {inclusive = true} }
                    }
                }
            )
        }

    }
}