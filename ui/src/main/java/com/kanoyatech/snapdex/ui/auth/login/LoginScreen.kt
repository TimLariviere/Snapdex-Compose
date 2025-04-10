package com.kanoyatech.snapdex.ui.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import com.kanoyatech.snapdex.designsystem.components.SnapdexBackground
import com.kanoyatech.snapdex.designsystem.components.SnapdexLinkButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexPasswordField
import com.kanoyatech.snapdex.designsystem.components.SnapdexPrimaryButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexTextField
import com.kanoyatech.snapdex.designsystem.pagePadding
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSuccessfulLogin: () -> Unit,
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }
            LoginEvent.LoginSuccessful -> {
                keyboardController?.hide()
                onSuccessfulLogin()
            }
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
        },
    )
}

@Composable
private fun LoginScreen(state: LoginState, onAction: (LoginAction) -> Unit) {
    SnapdexBackground {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.fillMaxSize().systemBarsPadding().pagePadding(),
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(
                    imageVector = Icons.App,
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.size(84.dp).align(Alignment.CenterHorizontally),
                )

                Text(
                    text = stringResource(id = R.string.snapdex).uppercase(),
                    style = SnapdexTheme.typography.heading1,
                    color = SnapdexTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                SnapdexTextField(
                    state = state.email,
                    hint = stringResource(id = R.string.email_hint),
                )

                SnapdexPasswordField(
                    state = state.password,
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onAction(LoginAction.OnTogglePasswordVisibility)
                    },
                    hint = stringResource(id = R.string.password_hint),
                )

                SnapdexLinkButton(
                    text = stringResource(id = R.string.forgot_password),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    onAction(LoginAction.OnForgotPasswordClick)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                SnapdexPrimaryButton(
                    text = stringResource(id = R.string.login),
                    enabled = state.canLogin,
                    isBusy = state.isLoginIn,
                ) {
                    onAction(LoginAction.OnLoginClick)
                }

                SnapdexLinkButton(
                    text = stringResource(id = R.string.create_an_account),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    onAction(LoginAction.OnRegisterClick)
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme { LoginScreen(state = LoginState(), onAction = {}) }
}
