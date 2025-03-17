package com.kanoyatech.snapdex.ui.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.designsystem.LinkButton
import com.kanoyatech.snapdex.theme.designsystem.PrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SecondaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPasswordField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTextField
import com.kanoyatech.snapdex.theme.pagePadding
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSuccessfulLogin: () -> Unit
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            LoginEvent.LoginSuccessful -> onSuccessfulLogin()
            else -> Unit
        }
    }

    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                LoginAction.OnRegisterClick -> onRegisterClick()
                LoginAction.OnForgotPasswordClick -> onForgotPasswordClick()
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .pagePadding()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.snapdex).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        SnapdexTextField(
            state = state.email,
            hint = stringResource(id = R.string.email_hint)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SnapdexPasswordField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(LoginAction.OnTogglePasswordVisibility)
                },
                hint = stringResource(id = R.string.password_hint)
            )

            LinkButton(
                text = stringResource(id = R.string.forgot_password),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                onAction(LoginAction.OnForgotPasswordClick)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrimaryButton(
                text = stringResource(id = R.string.login),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                onAction(LoginAction.OnLoginClick)
            }

            LinkButton(
                text = stringResource(id = R.string.create_an_account),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                onAction(LoginAction.OnRegisterClick)
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}