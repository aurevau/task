package com.example.composetask.module

import android.content.Context
import com.example.composetask.auth.AuthRepository
import com.example.composetask.auth.FirebaseAuthRepository
import com.example.composetask.presentation.sign_in.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthRepository(
        impl: FirebaseAuthRepository
    ): AuthRepository

    companion object {

        @Provides
        @Singleton
        fun provideGoogleAuthUiClient(
            @ApplicationContext context: Context
        ): GoogleAuthUiClient {
            return GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context)
            )
        }
    }
}
