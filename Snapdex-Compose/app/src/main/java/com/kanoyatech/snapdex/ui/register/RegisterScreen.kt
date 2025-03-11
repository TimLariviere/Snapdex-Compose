package com.kanoyatech.snapdex.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.ui.components.PrimaryButton
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            RegisterEvent.RegistrationSuccess -> onSuccessfulRegistration()
            else -> Unit
        }
    }

    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnBackClick -> onBackClick()
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .padding(paddingValues)
        ) {
            BasicTextField(
                state = state.email,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            )

            BasicSecureTextField(
                state = state.password,
                textObfuscationMode = if (state.isPasswordVisible) {
                    TextObfuscationMode.Visible
                } else {
                    TextObfuscationMode.RevealLastTyped
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = stringResource(id = R.string.register),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                onAction(RegisterAction.OnRegisterClick)
            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}