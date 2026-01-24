package com.example.composetask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetask.presentation.sign_in.GoogleAuthUiClient
import com.example.composetask.presentation.sign_in.SignInViewModel
import com.example.composetask.presentation.sign_in.profile.ProfileScreen
import com.example.composetask.ui.theme.AppTheme
import com.example.composetask.ui.theme.ComposeTaskTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var selectedTheme by rememberSaveable {
                mutableStateOf(AppTheme.BLOSSOM)
            }
            ComposeTaskTheme(
                theme = selectedTheme
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in"){
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if(googleAuthUiClient.getSignedInUser() != null){
                                    navController.navigate("profile")
                                }
                            }
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = {result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    navController.navigate("profile") {
                                        popUpTo("sign_in") { inclusive = true }
                                    }
                                }
                            }

                            LoginScreen(state = state,
                                onSignInClick =  {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        Log.d("SIGN_IN", "IntentSender = $signInIntentSender")

                                        if (signInIntentSender == null) {
                                            Log.e("SIGN_IN", "Sign in intent is NULL")
                                            return@launch
                                        }
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },
                                currentTheme = selectedTheme,
                                onThemeSelected = { selectedTheme = it })
                        }

                        composable("profile") {
                            ProfileScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()

                                        navController.navigate("sign_in") {
                                            popUpTo(0)
                                        }
                                    }
                                }
                            )
                        }
                    }


                }
            }
                }


    }
}
//
//@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun LoginScreenDarkPreview() {
//    ComposeTaskTheme(
//        theme = AppTheme.BLOSSOM,
//        darkTheme = false,
//        dynamicColor =  false
//    ) {
//
//        LoginScreen(
//            currentTheme = AppTheme.BLOSSOM,
//            onThemeSelected = {}
//        )
//    }
//}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ComposeTaskTheme {
//        Greeting("Android")
//    }
//}