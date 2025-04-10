package com.kanoyatech.snapdex.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasDigit: Boolean = false,
    val hasLowercase: Boolean = false,
    val hasUppercase: Boolean = false,
) {
    val isValid: Boolean
        get() = hasMinLength && hasDigit && hasLowercase && hasUppercase
}
