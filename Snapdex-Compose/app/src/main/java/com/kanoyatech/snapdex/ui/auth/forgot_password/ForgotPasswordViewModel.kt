package com.kanoyatech.snapdex.ui.auth.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel(
    val auth: FirebaseAuth
): ViewModel() {
    var state by mutableStateOf(ForgotPasswordState())
        private set

    fun onAction(action: ForgotPasswordAction) {

    }
}