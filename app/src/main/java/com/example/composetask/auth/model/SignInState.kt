package com.example.composetask.auth.model

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
) {
}