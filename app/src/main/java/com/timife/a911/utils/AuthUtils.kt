package com.timife.a911.utils

import android.util.Patterns

object AuthUtils {
    fun validateRegistrationDetails(
        fullName: String,
        email: String,
        password: String
    ): ValidationStates {
        return if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            ValidationStates.Error(Messages.BLANK_FIELDS_IN_FORM)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationStates.Error(Messages.INVALID_CREDENTIAL)
        } else if (password.length < Constants.PASSWORD_LENGTH) {
            ValidationStates.Error(Messages.PASSWORD_SHORT_IN_FORM)
        } else {
            ValidationStates.Success
        }
    }
}

sealed class ValidationStates {
    object Success: ValidationStates()
    data class Error(val message: String): ValidationStates()
}