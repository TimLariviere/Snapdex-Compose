package com.kanoyatech.snapdex.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.ui.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(
    private val auth: FirebaseAuth,
    private val dataSource: DataSource
): ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            try {
                val result =
                    auth.signInWithEmailAndPassword(
                        state.email.text.toString(),
                        state.password.text.toString()
                    ).await()

                val user = result.user!!

                dataSource.createUser(user.uid, name = user.displayName ?: "NO_NAME")
                dataSource.addPokemonToUser(userId = user.uid, 1)
                dataSource.addPokemonToUser(userId = user.uid, 2)
                dataSource.addPokemonToUser(userId = user.uid, 1)
                dataSource.addPokemonToUser(userId = user.uid, 6)
                dataSource.addPokemonToUser(userId = user.uid, 150)

                eventChannel.send(LoginEvent.LoginSuccessful)
            } catch (e: Exception) {
                eventChannel.send(LoginEvent.Error(UiText.StringResource(id = R.string.login_error)))
            }
        }
    }
}