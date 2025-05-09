package com.kanoyatech.snapdex.ui.main.profile_tab.new_password

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.components.PopupButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexPasswordField
import com.kanoyatech.snapdex.designsystem.components.SnapdexPopup
import com.kanoyatech.snapdex.designsystem.components.SnapdexPrimaryButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexScaffold
import com.kanoyatech.snapdex.designsystem.components.SnapdexTopAppBar
import com.kanoyatech.snapdex.designsystem.pagePadding
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.components.PasswordRequirements
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewPasswordScreenRoot(
    viewModel: NewPasswordViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var showPasswordChangedPopup by remember { mutableStateOf(false) }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is NewPasswordEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }
            NewPasswordEvent.PasswordChanged -> {
                keyboardController?.hide()
                showPasswordChangedPopup = true
            }
        }
    }

    NewPasswordScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                NewPasswordAction.OnBackClick -> onBackClick()
                else -> Unit
            }

            viewModel.onAction(action)
        },
    )

    if (showPasswordChangedPopup) {
        SnapdexPopup(
            title = stringResource(id = R.string.password_changed),
            description = stringResource(id = R.string.password_changed_description),
            primaryButton =
                PopupButton(
                    text = stringResource(id = R.string.ok),
                    onClick = {
                        showPasswordChangedPopup = false
                        onBackClick()
                    },
                ),
            onDismissRequest = {
                showPasswordChangedPopup = false
                onBackClick()
            },
        )
    }
}

@Composable
fun NewPasswordScreen(state: NewPasswordState, onAction: (NewPasswordAction) -> Unit) {
    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(R.string.new_password),
                onBackClick = { onAction(NewPasswordAction.OnBackClick) },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).pagePadding().fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SnapdexPasswordField(
                state = state.oldPassword,
                isPasswordVisible = state.isOldPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(NewPasswordAction.OnToggleOldPasswordVisibilityClick)
                },
                hint = stringResource(id = R.string.old_password),
            )

            SnapdexPasswordField(
                state = state.newPassword,
                isPasswordVisible = state.isNewPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(NewPasswordAction.OnToggleNewPasswordVisibilityClick)
                },
                hint = stringResource(id = R.string.new_password),
            )

            PasswordRequirements(state = state.newPasswordValidationState)

            Spacer(modifier = Modifier.weight(1f))

            SnapdexPrimaryButton(
                text = stringResource(R.string.set_new_password),
                onClick = { onAction(NewPasswordAction.OnSetPasswordClick) },
                enabled = state.canChangePassword,
                isBusy = state.isChangingPassword,
            )
        }
    }
}

@Preview
@Composable
private fun NewPasswordScreenPreview() {
    AppTheme { NewPasswordScreen(state = NewPasswordState(), onAction = {}) }
}
