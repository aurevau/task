package com.example.composetask.navigation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetask.auth.ui.AuthTabs
import com.example.composetask.auth.ui.LoginScreen
import com.example.composetask.auth.ui.SignUpScreen
import com.example.composetask.auth.viewmodel.AuthViewModel
import com.example.composetask.chat.ui.ChatScreen
import com.example.composetask.home.ui.HomeScreen
import com.example.composetask.presentation.sign_in.profile.ProfileScreen
import com.example.composetask.ui.theme.AppTheme
import kotlinx.coroutines.launch


@Composable
fun NavGraph(
    currentTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar(navController)) {
                BottomNavigationBar(navController)
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "auth",
            modifier = Modifier.padding(padding)
        ) {
            navigation(startDestination = "sign_in", route = "auth") {
                composable("sign_in") {
                    Column {
                        AuthTabs(navController)

                        val authViewModel: AuthViewModel = hiltViewModel()
                        val state by authViewModel.loginState.collectAsStateWithLifecycle()

                        val currentUser by authViewModel.currentUser.collectAsStateWithLifecycle()

                        LaunchedEffect(currentUser) {
                            if (currentUser != null) {
                                navController.navigate("home") {
                                    popUpTo("auth") { inclusive = true }
                                }
                            }
                        }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    authViewModel.handleGoogleSignInResult(result.data)
                                } else {
                                    authViewModel.onGoogleSignInCanceled()
                                }
                            }
                        )


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
                                navController.navigate("signup") {
                                    popUpTo("auth") {
                                        inclusive = false
                                    }
                                }
                            }
                        )
                    }


                }
                composable("signup") {
                    val authViewModel: AuthViewModel = hiltViewModel()
                    val state by authViewModel.loginState.collectAsStateWithLifecycle()
                    val currentUser by authViewModel.currentUser.collectAsStateWithLifecycle()

                    LaunchedEffect(currentUser) {
                        if (currentUser != null) {
                            navController.navigate("home") {
                                popUpTo("auth") { inclusive = true }
                            }
                        }
                    }
                    Column {
                        AuthTabs(navController)
                        SignUpScreen(
                            state = state,
                            onSignUp = { username, email, password, photoUrl ->
                                authViewModel.signUp(username, email, password, photoUrl)
                            }, onNavigateToLogin = { navController.navigate("sign_in") })
                    }
                }

                composable("home") { HomeScreen(navController) }
                composable("profile") {
                    val authViewModel: AuthViewModel = hiltViewModel()
                    val user by authViewModel.currentUser.collectAsStateWithLifecycle()

                    ProfileScreen(
                        userData = user,
                        onSignOut = {
                            scope.launch {
                                authViewModel.signOut()
                                navController.navigate("sign_in") {
                                    popUpTo("profile") { inclusive = true }
                                }
                            }
                        }
                    )
                }
                composable(
                    "chat/{channelId}", arguments = listOf(
                    navArgument("channelId") {
                        type = NavType.StringType
                    }
                )) {
                    val channelId = it.arguments?.getString("channelId") ?: ""
                    ChatScreen(navController, channelId)

                }

            }


        }
    }




}

@Composable
fun shouldShowBottomBar(navController: NavHostController): Boolean {
    val route =
        navController.currentBackStackEntryAsState().value?.destination?.route

    return route in listOf(
        "home",
        "profile"
    )
}