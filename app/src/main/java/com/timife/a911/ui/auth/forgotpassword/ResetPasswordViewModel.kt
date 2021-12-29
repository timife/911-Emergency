package com.timife.a911.ui.auth.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.timife.a911.createProfile.ui.AuthRepository
import com.timife.a911.utils.states.DataState
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(private val authRepository: AuthRepository)  : ViewModel() {
    fun resetPassword(email: String): LiveData<DataState<String>> {
        return authRepository.sendPasswordResetLink(email).asLiveData()
    }
}