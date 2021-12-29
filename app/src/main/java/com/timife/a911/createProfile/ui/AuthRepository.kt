package com.timife.a911.createProfile.ui

import com.google.firebase.auth.FirebaseUser
import com.timife.a911.utils.states.AuthResult
import com.timife.a911.utils.states.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createUser(
        fullName: String,
        email: String,
        password: String,
        confirmPassword:String
    ): Flow<AuthResult<String>>

    fun currentUserId(): String

    fun loginUser(
        email: String,
        password: String
    ): Flow<AuthResult<String>>

    suspend fun sendEmailVerificationLink(firebaseUser: FirebaseUser)

    fun logOut()

    fun sendPasswordResetLink(email: String): Flow<DataState<String>>

    fun sendEmailVerificationLink(email: String, password: String): Flow<DataState<String>>
}