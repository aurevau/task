package com.example.composetask.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composetask.auth.model.SignInResult
import com.example.composetask.auth.model.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
       _state.update { it.copy(
           isSignInSuccessful = result.data != null,
           signInError = result.errorMessage
       ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}