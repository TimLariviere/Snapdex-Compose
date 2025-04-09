package com.kanoyatech.snapdex.domain

import java.util.regex.Pattern

class UserDataValidator {
    companion object {
        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+\$"
        )!!
    }

    fun validateName(name: String): Boolean {
        val isNotBlank = name.isNotBlank()
        return isNotBlank
    }

    fun validateEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
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