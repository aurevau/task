package com.example.composetask.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String)

    suspend fun signUp(email: String, password: String)
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}