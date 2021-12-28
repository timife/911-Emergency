package com.timife.a911.data.repository

import androidx.lifecycle.LiveData
import com.timife.a911.data.model.User
import com.timife.a911.utils.states.AuthResult
import com.timife.a911.utils.states.DataState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {
    fun checkLoginStatus(): LiveData<AuthResult<String>>

    fun getLoggedInUserDetails(userId: String): Flow<DataState<User>>

    fun getUserId(): String

    fun updateProfileImage(file: File, userId: String): Flow<DataState<String>>

}