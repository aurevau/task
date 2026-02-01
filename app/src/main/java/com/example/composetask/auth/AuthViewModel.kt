package com.example.composetask.auth

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetask.login.LoginState
import com.example.composetask.presentation.sign_in.GoogleAuthUiClient
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
): ViewModel() {
    private val _authProvider = MutableStateFlow<AuthProvider?>(null)
    val authProvider: StateFlow<AuthProvider?> = _authProvider
    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        val user = authRepository.getCurrentUser()
        _currentUser.value = user

        val isGoogle = user?.providerData?.any { it.providerId == "google.com" } == true
        _authProvider.value =
            when {
                user == null -> null
                isGoogle -> AuthProvider.GOOGLE
                else -> AuthProvider.EMAIL
            }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                authRepository.login(email, password)
                _currentUser.value = authRepository.getCurrentUser()
                _loginState.value = LoginState.Success
                _authProvider.value = AuthProvider.EMAIL
            } catch (exception: Exception) {
                _loginState.value = LoginState.Error(exception.message ?: "Login failed")
            }

        }
    }

    fun signUp(username: String, email: String, password: String, photoUrl: String?) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                authRepository.signUp(email, password)
                authRepository.updateProfile(username, photoUrl)
                val updatedUser = authRepository.getCurrentUser()
                _currentUser.value = updatedUser
                _authProvider.value = AuthProvider.EMAIL
                _loginState.value = LoginState.Success

            }  catch (exception: Exception) {
                _loginState.value = LoginState.Error(exception.message ?: "Sign up failed")

            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }

    suspend fun getGoogleSignInIntent(): IntentSender? {
        _loginState.value = LoginState.Loading
        return googleAuthUiClient.signIn()
    }

    fun handleGoogleSignInResult(data: Intent?) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                val signInResult = googleAuthUiClient.signInWithIntent(data)

                if(signInResult.data != null) {
                    _currentUser.value = authRepository.getCurrentUser()
                    _authProvider.value = AuthProvider.GOOGLE
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error(
                        signInResult.errorMessage ?: "Google sign in failed"
                    )
                }
            } catch (exception: Exception) {
                _loginState.value = LoginState.Error(
                    exception.message ?: "Google sign in failed"
                )
            }
        }
    }



    fun onGoogleSignInCanceled() {
        _loginState.value = LoginState.Idle
    }

    fun signOut() {
        viewModelScope.launch {

            when (_authProvider.value) {
                AuthProvider.GOOGLE -> {
                    googleAuthUiClient.signOut()
                }
                AuthProvider.EMAIL -> {
                    authRepository.logout()
                }
                else -> {
                    authRepository.logout()
                }
            }

            _currentUser.value = null
            _loginState.value = LoginState.Idle
        }
    }




}