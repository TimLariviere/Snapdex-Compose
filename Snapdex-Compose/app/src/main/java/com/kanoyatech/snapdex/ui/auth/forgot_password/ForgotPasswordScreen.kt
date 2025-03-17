package com.kanoyatech.snapdex.ui.auth.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.designsystem.PrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTextField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTopAppBar
import com.kanoyatech.snapdex.theme.pagePadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreenRoot(
    viewModel: ForgotPasswordViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    ForgotPasswordScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ForgotPasswordAction.OnBackClick -> onBackClick()
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@Composable
private fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit
) {
    Scaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(id = R.string.forgotten_password),
                onBackClick = {
                    onAction(ForgotPasswordAction.OnBackClick)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .pagePadding(top = 84.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.password_reset_link),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            SnapdexTextField(
                state = state.email,
                hint = stringResource(id = R.string.email_hint),
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = stringResource(id = R.string.send_password_reset_link),
                enabled = state.canSendEmail && !state.isSendingEmail,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                onAction(ForgotPasswordAction.OnSendEmailClick)
            }
        }
    }
}

@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
     AppTheme {
        ForgotPasswordScreen(
            state = ForgotPasswordState(),
            onAction = {}
        )
    }
}