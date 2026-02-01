package com.example.composetask.auth

import android.net.Uri
import com.example.composetask.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    suspend fun login(email: String, password: String)

    suspend fun signUp(email: String, password: String)
    fun logout()
    fun getCurrentUser(): FirebaseUser?

    suspend fun updateProfile(username: String, photoUrl: String?)
    fun getUserDataFromAuth(): User?
}