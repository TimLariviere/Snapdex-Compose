@file:OptIn(ExperimentalMaterial3Api::class)

package com.kanoyatech.snapdex.ui.auth.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.SnapdexTheme
import com.kanoyatech.snapdex.theme.designsystem.PasswordRequirements
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPasswordField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexScaffold
import com.kanoyatech.snapdex.theme.designsystem.SnapdexText
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTextField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTopAppBar
import com.kanoyatech.snapdex.theme.pagePadding
import com.kanoyatech.snapdex.ui.AvatarUi
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                onSuccessfulRegistration()
            }
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
    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(id = R.string.create_an_account),
                onBackClick = {
                    onAction(RegisterAction.OnBackClick)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pagePadding()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PickPictureButton(
                    state = state,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    onAction(RegisterAction.OnOpenAvatarPicker)
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SnapdexText(
                        text = stringResource(id = R.string.name_hint),
                        style = MaterialTheme.typography.bodySmall
                    )

                    SnapdexTextField(
                        state = state.name
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SnapdexText(
                        text = stringResource(id = R.string.email_hint),
                        style = MaterialTheme.typography.bodySmall
                    )

                    SnapdexTextField(
                        state = state.email,
                        keyboardType = KeyboardType.Email
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SnapdexText(
                        text = stringResource(id = R.string.password_hint),
                        style = MaterialTheme.typography.bodySmall
                    )

                    SnapdexPasswordField(
                        state = state.password,
                        isPasswordVisible = state.isPasswordVisible,
                        onTogglePasswordVisibility = {
                            onAction(RegisterAction.OnTogglePasswordVisibility)
                        }
                    )

                    PasswordRequirements(
                        state = state.passwordValidationState
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                SnapdexPrimaryButton(
                    text = stringResource(id = R.string.create_account),
                    enabled = state.canRegister,
                    isBusy = state.isRegistering,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    onAction(RegisterAction.OnRegisterClick)
                }
            }

            if (state.showAvatarPicker) {
                AvatarPickerDialog(
                    selected = state.avatar,
                    onSelectionChange = { avatar ->
                        onAction(RegisterAction.OnAvatarSelectionChange(avatar))
                    },
                    onDismissRequest = {
                        onAction(RegisterAction.OnCloseAvatarPicker)
                    }
                )
            }
        }
    }
}

@Composable
private fun PickPictureButton(
    state: RegisterState,
    modifier: Modifier = Modifier,
    onPickAvatarClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(SnapdexTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = SnapdexTheme.colorScheme.outline,
                shape = CircleShape
            )
            .size(88.dp)
            .clickable(enabled = !state.isRegistering) {
                onPickAvatarClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (state.avatar == -1) {
            Icon(
                imageVector = Icons.Add,
                contentDescription = null,
                tint = SnapdexTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(48.dp)
            )
        } else {
            Image(
                painter = painterResource(id = AvatarUi.getFor(state.avatar)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(
            state = RegisterState(avatar = 1),
            onAction = {}
        )
    }
}