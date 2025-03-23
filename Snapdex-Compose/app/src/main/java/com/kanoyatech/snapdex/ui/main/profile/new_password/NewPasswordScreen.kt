package com.kanoyatech.snapdex.ui.main.profile.new_password

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.designsystem.PasswordRequirements
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPopup
import com.kanoyatech.snapdex.theme.designsystem.PopupButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPasswordField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexScaffold
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTopAppBar
import com.kanoyatech.snapdex.theme.pagePadding
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewPasswordScreenRoot(
    viewModel: NewPasswordViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is NewPasswordEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            NewPasswordEvent.PasswordChanged -> {
                keyboardController?.hide()
            }
        }
    }

    NewPasswordScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                NewPasswordAction.OnBackClick -> onBackClick()
                NewPasswordAction.OnPasswordChangedPopupDismiss -> onBackClick()
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@Composable
fun NewPasswordScreen(
    state: NewPasswordState,
    onAction: (NewPasswordAction) -> Unit
) {
    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(R.string.new_password),
                onBackClick = { onAction(NewPasswordAction.OnBackClick) }
            )
        }
    ) { paddingValues ->
        Box {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .pagePadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SnapdexPasswordField(
                    state = state.oldPassword,
                    isPasswordVisible = state.isOldPasswordVisible,
                    onTogglePasswordVisibility = { onAction(NewPasswordAction.OnToggleOldPasswordVisibilityClick) },
                    hint = stringResource(id = R.string.old_password)
                )

                SnapdexPasswordField(
                    state = state.newPassword,
                    isPasswordVisible = state.isNewPasswordVisible,
                    onTogglePasswordVisibility = { onAction(NewPasswordAction.OnToggleNewPasswordVisibilityClick) },
                    hint = stringResource(id = R.string.new_password)
                )

                PasswordRequirements(
                    state = state.newPasswordValidationState
                )

                Spacer(modifier = Modifier.weight(1f))

                SnapdexPrimaryButton(
                    text = stringResource(R.string.set_new_password),
                    onClick = { onAction(NewPasswordAction.OnSetPasswordClick) },
                    enabled = state.canChangePassword,
                    isBusy = state.isChangingPassword,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            if (state.showPasswordChangedPopup) {
                SnapdexPopup(
                    title = stringResource(id = R.string.password_changed),
                    description = stringResource(id = R.string.password_changed_description),
                    primaryButton = PopupButton(
                        text = stringResource(id = R.string.ok),
                        onClick = { onAction(NewPasswordAction.OnPasswordChangedPopupDismiss) }
                    ),
                    onDismissRequest = { onAction(NewPasswordAction.OnPasswordChangedPopupDismiss) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun NewPasswordScreenPreview() {
    AppTheme {
        NewPasswordScreen(
            state = NewPasswordState(),
            onAction = {}
        )
    }
}