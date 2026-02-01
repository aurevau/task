package com.example.composetask.auth

import android.net.Uri
import android.util.Log
import com.example.composetask.User
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

    override fun getUserDataFromAuth(): User? {


        val user = FirebaseAuth.getInstance().currentUser

        return user?.let {
            Log.d(
                "AUTH",
                "uid=${it.uid}, display=${it.displayName}, email=${it.email}, photo=${it.photoUrl}"
            )
            val name =
                it.displayName
                    ?: it.email
                    ?: it.uid.take(6)


            val photo =
                it.photoUrl
                    ?.toString()
                    ?.takeIf { url ->
                        url.isNotBlank() && url != "null"
                    }
            User(
                username = name,
                userId = it.uid,
                profilePictureUrl = photo
            )
        }

    }

    override fun getCurrentUser(): FirebaseUser? =
        auth.currentUser

}