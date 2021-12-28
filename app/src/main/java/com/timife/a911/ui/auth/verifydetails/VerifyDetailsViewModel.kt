package com.timife.a911.ui.auth.verifydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.timife.a911.createProfile.ui.AuthRepository
import com.timife.a911.utils.states.DataState
import javax.inject.Inject

class VerifyDetailsViewModel @Inject constructor(private val authRepository:AuthRepository) : ViewModel() {

    fun resendVerificationLink(email: String, password: String): LiveData<DataState<String>> {
        return authRepository.sendEmailVerificationLink(email, password).asLiveData()
    }
}