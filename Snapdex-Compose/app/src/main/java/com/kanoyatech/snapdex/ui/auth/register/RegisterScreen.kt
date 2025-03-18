@file:OptIn(ExperimentalMaterial3Api::class)

package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import coil.compose.AsyncImage
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.designsystem.PrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPasswordField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTextField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTopAppBar
import com.kanoyatech.snapdex.theme.pagePadding
import com.kanoyatech.snapdex.ui.AvatarUi
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import kotlinx.coroutines.flow.single
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onPickAvatarClick: () -> Unit,
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
                RegisterAction.OnPickAvatarClick -> onPickAvatarClick()
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
    Scaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(id = R.string.create_an_account),
                onBackClick = {
                    onAction(RegisterAction.OnBackClick)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pagePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PickPictureButton(
                state = state,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                onAction(RegisterAction.OnPickAvatarClick)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
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
                Text(
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
                Text(
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
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = stringResource(id = R.string.create_account),
                enabled = state.canRegister && !state.isRegistering,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                onAction(RegisterAction.OnRegisterClick)
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
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
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