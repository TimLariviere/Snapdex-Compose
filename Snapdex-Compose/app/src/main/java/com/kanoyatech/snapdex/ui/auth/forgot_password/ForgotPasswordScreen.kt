package com.kanoyatech.snapdex.ui.auth.forgot_password

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexScaffold
import com.kanoyatech.snapdex.theme.designsystem.SnapdexText
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTextField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTopAppBar
import com.kanoyatech.snapdex.theme.pagePadding
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreenRoot(
    viewModel: ForgotPasswordViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ForgotPasswordEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
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
        }
    )
}

@Composable
private fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit
) {
    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(id = R.string.forgotten_password),
                onBackClick = {
                    onAction(ForgotPasswordAction.OnBackClick)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pagePadding(top = 84.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SnapdexText(
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

                SnapdexPrimaryButton(
                    text = stringResource(id = R.string.send_password_reset_link),
                    enabled = state.canSendEmail && !state.isSendingEmail,
                    isBusy = state.isSendingEmail,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    onAction(ForgotPasswordAction.OnSendEmailClick)
                }
            }

            if (state.showEmailSent) {
                EmailSentDialog(
                    onDismissRequest = { onAction(ForgotPasswordAction.OnPopupDismissClick) },
                    onConfirmClick = { onAction(ForgotPasswordAction.OnBackClick) }
                )
            }
        }
    }
}

@Composable
private fun EmailSentDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SnapdexText(
                text = stringResource(id = R.string.password_reset),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            SnapdexText(
                text = stringResource(id = R.string.check_mailbox_password_reset_link),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            SnapdexPrimaryButton(
                text = stringResource(id = R.string.ok),
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
     AppTheme {
        ForgotPasswordScreen(
            state = ForgotPasswordState(
                showEmailSent = true
            ),
            onAction = {}
        )
    }
}