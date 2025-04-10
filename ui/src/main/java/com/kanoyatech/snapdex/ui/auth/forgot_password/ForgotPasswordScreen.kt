package com.kanoyatech.snapdex.ui.auth.forgot_password

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.components.PopupButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexPopup
import com.kanoyatech.snapdex.designsystem.components.SnapdexPrimaryButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexScaffold
import com.kanoyatech.snapdex.designsystem.components.SnapdexTextField
import com.kanoyatech.snapdex.designsystem.components.SnapdexTopAppBar
import com.kanoyatech.snapdex.designsystem.pagePadding
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreenRoot(
    viewModel: ForgotPasswordViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ForgotPasswordEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }
        }
    }

    ForgotPasswordScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ForgotPasswordAction.OnBackClick -> onBackClick()
                else -> Unit
            }

            viewModel.onAction(action)
        },
    )
}

@Composable
private fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit,
) {
    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(id = R.string.forgotten_password),
                onBackClick = { onAction(ForgotPasswordAction.OnBackClick) },
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues).pagePadding(top = 84.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.password_reset_link),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                SnapdexTextField(
                    state = state.email,
                    hint = stringResource(id = R.string.email_hint),
                    keyboardType = KeyboardType.Email,
                )

                Spacer(modifier = Modifier.weight(1f))

                SnapdexPrimaryButton(
                    text = stringResource(id = R.string.send_password_reset_link),
                    enabled = state.canSendEmail && !state.isSendingEmail,
                    isBusy = state.isSendingEmail,
                ) {
                    onAction(ForgotPasswordAction.OnSendEmailClick)
                }
            }

            if (state.showEmailSent) {
                EmailSentDialog(
                    onDismissRequest = { onAction(ForgotPasswordAction.OnPopupDismissClick) },
                    onConfirmClick = { onAction(ForgotPasswordAction.OnBackClick) },
                )
            }
        }
    }
}

@Composable
private fun EmailSentDialog(onDismissRequest: () -> Unit, onConfirmClick: () -> Unit) {
    SnapdexPopup(
        title = stringResource(id = R.string.password_reset),
        description = stringResource(id = R.string.check_mailbox_password_reset_link),
        primaryButton =
            PopupButton(text = stringResource(id = R.string.ok), onClick = onConfirmClick),
        onDismissRequest = onDismissRequest,
    )
}

@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
    AppTheme {
        ForgotPasswordScreen(state = ForgotPasswordState(showEmailSent = true), onAction = {})
    }
}
