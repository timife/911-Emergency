package com.timife.a911.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.timife.a911.createProfile.ui.AuthRepository
import com.timife.a911.utils.Event
import com.timife.a911.utils.states.AuthResult
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // snack bar event
    private val _showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBar: LiveData<Event<String>>
        get() = _showSnackBar

    fun snackBarMessage(message: String) {
        _showSnackBar.value =
            Event(message)
    }

    fun loginUser(
        email: String,
        password: String
    ): LiveData<AuthResult<String>> {
        return authRepository.loginUser(email, password).asLiveData()
    }
}