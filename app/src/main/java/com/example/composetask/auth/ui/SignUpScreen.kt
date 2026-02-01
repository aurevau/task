package com.example.composetask.auth.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.composetask.auth.viewmodel.AuthViewModel
import com.example.composetask.auth.model.LoginState
import com.example.composetask.core.util.InitialsAvatar
import kotlin.Unit

@Composable
fun SignUpScreen(state: LoginState,
                 onSignUp: (String, String, String, String?) -> Unit,
                 onNavigateToLogin: () -> Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }


    val authViewModel: AuthViewModel = hiltViewModel()
    val state by authViewModel.loginState.collectAsStateWithLifecycle()

    val isLoading = state is LoginState.Loading


    val imagePicker =
        rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            selectedImageUri = uri
        }

    val isLoginEnabled =
        email.isNotBlank() &&
                password.isNotBlank() &&
                !isLoading

    Box(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(16.dp),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Sign Up", style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(24.dp))


            Box(modifier = Modifier.size(120.dp)
                .clip(CircleShape)
                .clickable{imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))}
                .background(MaterialTheme.colorScheme.onBackground),
                contentAlignment = Alignment.Center) {

                if (selectedImageUri != null) {
                    Image(painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "profilePicture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize())
                } else {
                    InitialsAvatar(username)
                }
            }

            OutlinedTextField(
                value = username,
                onValueChange = {username = it},
                label = {Text ("Username")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {Text ("Email")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {Text ("Password")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            val context = LocalContext.current

            Button(onClick = {
                onSignUp(username, email, password, selectedImageUri.toString())
//            authViewModel.signUp(username, email, password, photoUrl = selectedImageUri?.toString())

            }, enabled = isLoginEnabled,



                ) {  if (isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.background
                )
            } else {

                Text("Create account")

            }


            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = {
                onNavigateToLogin()
            }) { Text("Already have an account? Login") }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview() {
//
//        SignUpScreen()
//
//}