package com.timife.a911.createProfile.ui

import androidx.lifecycle.LiveData
import com.timife.a911.utils.states.AuthResult

interface AuthRepository {
    fun createUser(
        fullName: String,
        email:String,
        password:String
    ): LiveData<AuthResult<String>>

    fun currentUserId(): String
}