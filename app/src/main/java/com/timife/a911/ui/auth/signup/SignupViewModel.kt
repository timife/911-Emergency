package com.timife.a911.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.timife.a911.createProfile.ui.AuthRepository
import com.timife.a911.utils.Event
import com.timife.a911.utils.states.AuthResult
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
)  : ViewModel() {

    private val _showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBar: LiveData<Event<String>>
        get() = _showSnackBar

    fun snackBarMessage(message: String) {
        _showSnackBar.value = Event(message)

    }

    fun registerNewUser(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): LiveData<AuthResult<String>> {
        return authRepository.createUser(fullName, email, password, confirmPassword).asLiveData()
    }
    // TODO: Implement the ViewModel
}