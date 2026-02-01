package com.example.composetask

import android.app.Activity.RESULT_OK
import android.os.Bundle
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetask.auth.viewmodel.AuthViewModel
import com.example.composetask.chat.ui.ChatScreen
import com.example.composetask.auth.ui.LoginScreen
import com.example.composetask.auth.ui.AuthTabs
import com.example.composetask.home.ui.HomeScreen
import com.example.composetask.auth.ui.SignUpScreen
import com.example.composetask.navigation.NavGraph
import com.example.composetask.presentation.sign_in.profile.ProfileScreen
import com.example.composetask.ui.theme.AppTheme
import com.example.composetask.ui.theme.ComposeTaskTheme
import com.google.firebase.FirebaseApp
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
                NavGraph(
                    currentTheme = selectedTheme,
                    onThemeSelected = { selectedTheme = it }
                )

            }
        }


    }
}


