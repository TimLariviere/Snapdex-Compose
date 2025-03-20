package com.kanoyatech.snapdex.domain

import android.util.Patterns

class UserDataValidator {
    fun validateName(name: String): Boolean {
        val isNotBlank = name.isNotBlank()
        return isNotBlank
    }

    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length > 8
        val hasDigit = password.any { it.isDigit() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasUppercase = password.any { it.isUpperCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasDigit = hasDigit,
            hasLowercase = hasLowercase,
            hasUppercase = hasUppercase
        )
    }
}