package com.timife.a911.createProfile.ui

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.timife.a911.utils.states.AuthResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val remoteDatabase: FirebaseDatabase
) : AuthRepository{
    override fun createUser(
        fullName: String,
        email: String,
        password: String
    ): LiveData<AuthResult<String>> {
        TODO("Not yet implemented")
    }

    override fun currentUserId(): String {
        TODO("Not yet implemented")
    }

}