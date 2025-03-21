package com.kanoyatech.snapdex.ui.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.designsystem.AvatarView
import com.kanoyatech.snapdex.theme.designsystem.Popup
import com.kanoyatech.snapdex.theme.designsystem.PopupButton
import com.kanoyatech.snapdex.ui.main.profile.components.DestructiveSettingsButton
import com.kanoyatech.snapdex.ui.main.profile.components.SettingsButton
import com.kanoyatech.snapdex.ui.main.profile.components.SettingsPickerButton
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreenRoot(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = koinViewModel(),
    onLoggedOut: () -> Unit,
    onChangeNameClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onCreditsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ProfileEvent.LoggedOut -> onLoggedOut()
        }
    }

    ProfileScreen(
        paddingValues = paddingValues,
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ProfileAction.OnChangeNameClick -> onChangeNameClick()
                ProfileAction.OnChangePasswordClick -> onChangePasswordClick()
                ProfileAction.OnCreditsClick -> onCreditsClick()
                ProfileAction.OnPrivacyPolicyClick -> onPrivacyPolicyClick()
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@Composable
private fun ProfileScreen(
    paddingValues: PaddingValues,
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                AvatarView(
                    avatarId = state.user.avatarId,
                    isSelected = false,
                    modifier = Modifier
                        .height(64.dp)
                )

                Column {
                    Text(
                        text = state.user.name,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = state.user.email,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    AccountSettings(state, onAction)
                    AppSettings(onAction)
                    About(onAction)
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    ) {
                        DestructiveSettingsButton(stringResource(id = R.string.logout)) {
                            onAction(ProfileAction.OnLogoutClick)
                        }
                    }
                }
            }

            CallToAction(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }

        if (state.showProgressResetDialog) {
            ProgressResetConfirmationDialog(onAction)
        }

        if (state.showAccountDeletionDialog) {
            AccountDeletionConfirmationDialog(onAction)
        }
    }
}

@Composable
private fun AccountSettings(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.account_settings),
            style = MaterialTheme.typography.titleSmall
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            SettingsPickerButton(
                text = stringResource(id = R.string.account_name),
                value = state.user.name,
                onClick = { onAction(ProfileAction.OnChangeNameClick) }
            )

            HorizontalDivider()

            SettingsButton(
                text = stringResource(id = R.string.change_password),
                onClick = { onAction(ProfileAction.OnChangePasswordClick) }
            )

            HorizontalDivider()

            SettingsButton(
                text = stringResource(id = R.string.reset_progress),
                onClick = { onAction(ProfileAction.OnResetProgressClick) }
            )

            HorizontalDivider()

            DestructiveSettingsButton(
                text = stringResource(id = R.string.delete_account),
                onClick = { onAction(ProfileAction.OnDeleteAccountClick) }
            )
        }
    }
}

@Composable
private fun AppSettings(
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.app_settings),
            style = MaterialTheme.typography.titleSmall
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            SettingsPickerButton(
                text = stringResource(id = R.string.ai_model),
                value = "On device",
                onClick = { onAction(ProfileAction.OnChangeAiModelClick) }
            )

            HorizontalDivider()

            SettingsPickerButton(
                text = stringResource(id = R.string.language),
                value = "English",
                onClick = { onAction(ProfileAction.OnChangeLanguageClick) }
            )

            HorizontalDivider()

            SettingsPickerButton(
                text = stringResource(id = R.string.notifications),
                value = "Disabled",
                onClick = { onAction(ProfileAction.OnChangeNotificationsClick) }
            )
        }
    }
}

@Composable
private fun About(
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.titleSmall
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            SettingsButton(
                text = stringResource(id = R.string.licenses_and_credits),
                onClick = { onAction(ProfileAction.OnCreditsClick) }
            )

            HorizontalDivider()

            SettingsButton(
                text = stringResource(id = R.string.privacy_policy),
                onClick = { onAction(ProfileAction.OnPrivacyPolicyClick) }
            )
        }
    }
}

@Composable
private fun CallToAction(modifier: Modifier = Modifier) {
    val styles =
        TextLinkStyles(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        )

    Text(
        text = buildAnnotatedString {
            append(stringResource(id = R.string.built_in_the_open))
            withLink(LinkAnnotation.Url("https://github.com/timlariviere/snapdex", styles)) {
                append(stringResource(id = R.string.snapdex_on_github))
            }
            append(stringResource(id = R.string.get_in_touch))
            withLink(LinkAnnotation.Url("https://timothelariviere.com", styles)) {
                append(stringResource(id = R.string.website_link))
            }
        },
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
private fun ProgressResetConfirmationDialog(onAction: (ProfileAction) -> Unit) {
    Popup(
        title = stringResource(id = R.string.progress_reset),
        description = stringResource(id = R.string.progress_reset_description),
        isDestructive = true,
        onDismissRequest = { onAction(ProfileAction.OnProgressResetCancel) },
        primaryButton = PopupButton(
            text = stringResource(id = R.string.reset),
            onClick = { onAction(ProfileAction.OnProgressResetConfirm) }
        ),
        secondaryButton = PopupButton(
            text = stringResource(id = R.string.cancel),
            onClick = { onAction(ProfileAction.OnProgressResetCancel) }
        )
    )
}

@Composable
private fun AccountDeletionConfirmationDialog(onAction: (ProfileAction) -> Unit) {
    Popup(
        title = stringResource(id = R.string.account_deletion),
        description = stringResource(id = R.string.account_deletion_description),
        isDestructive = true,
        onDismissRequest = { onAction(ProfileAction.OnAccountDeletionCancel) },
        primaryButton = PopupButton(
            text = stringResource(id = R.string.delete),
            onClick = { onAction(ProfileAction.OnAccountDeletionConfirm) }
        ),
        secondaryButton = PopupButton(
            text = stringResource(id = R.string.cancel),
            onClick = { onAction(ProfileAction.OnAccountDeletionCancel) }
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            paddingValues = PaddingValues(0.dp),
            state = ProfileState(
                user = User(
                    id = "",
                    avatarId = 4,
                    name = "Roger",
                    email = "roger@snapdex.com"
                )
            ),
            onAction = {}
        )
    }
}