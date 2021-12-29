package com.timife.a911.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.timife.a911.data.model.User
import com.timife.a911.utils.Messages
import com.timife.a911.utils.auth.FirebaseUserLiveData
import com.timife.a911.utils.states.AuthResult
import com.timife.a911.utils.states.DataState
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
) : UserRepository {
    override fun checkLoginStatus(): LiveData<AuthResult<String>> {
        return FirebaseUserLiveData(
            auth
        ).map { user ->
            if (user.isEmailVerified) {
                AuthResult.Success(Messages.GENERIC_SUCCESS)
            } else if (!user.isEmailVerified) {
                auth.signOut()
                AuthResult.Failed(Messages.VERIFY_EMAIL)
            } else {
                AuthResult.Failed(Messages.LOGIN_FAILED)
            }
        }
    }

    override fun getLoggedInUserDetails(userId: String): Flow<DataState<User>> {
        TODO("Not yet implemented")
    }

    override fun getUserId(): String {
        TODO("Not yet implemented")
    }

    override fun updateProfileImage(file: File, userId: String): Flow<DataState<String>> {
        TODO("Not yet implemented")
    }
}