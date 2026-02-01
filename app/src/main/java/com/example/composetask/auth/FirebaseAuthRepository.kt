package com.example.composetask.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {
    override suspend fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override fun logout() {
        auth.signOut()
    }

    override suspend fun updateProfile(username: String, photoUrl: String? ) {
        val user = Firebase.auth.currentUser ?: return

        user.updateProfile(
            userProfileChangeRequest {
                displayName = username
                photoUrl?.let {
                    photoUri = Uri.parse(it)
                }
            }
        ).await()
        user.reload().await()

    }

    override fun getCurrentUser(): FirebaseUser? =
        auth.currentUser

}