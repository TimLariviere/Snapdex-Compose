package com.kanoyatech.snapdex.ui.main.profile

import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.kanoyatech.snapdex.domain.AIModel
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme
import com.kanoyatech.snapdex.theme.designsystem.PopupButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexBackground
import com.kanoyatech.snapdex.theme.designsystem.SnapdexDialogPicker
import com.kanoyatech.snapdex.theme.designsystem.SnapdexHorizontalDivider
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPopup
import com.kanoyatech.snapdex.ui.components.AvatarView
import com.kanoyatech.snapdex.ui.main.profile.components.DestructiveSettingsButton
import com.kanoyatech.snapdex.ui.main.profile.components.SettingsButton
import com.kanoyatech.snapdex.ui.main.profile.components.SettingsPickerButton
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import com.kanoyatech.snapdex.ui.utils.getLocale
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun ProfileScreenRoot(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = koinViewModel(),
    onLoggedOut: () -> Unit,
    onChangeNameClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onChangeAIModelClick: () -> Unit,
    onCreditsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale = configuration.getLocale()

    LaunchedEffect(locale) {
        viewModel.onAction(ProfileAction.OnLanguageChange(locale))
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ProfileEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
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
                ProfileAction.OnChangeAIModelClick -> onChangeAIModelClick()
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
                        text = "Maximilian", //state.user.name,
                        style = SnapdexTheme.typography.heading3
                    )
                    Text(
                        text = "maximilian@snapdex.com", //state.user.email,
                        style = SnapdexTheme.typography.largeLabel
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
                    AppSettings(state, onAction)
                    About(onAction)
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(SnapdexTheme.colorScheme.surface.copy(alpha = 0.3f))
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

        if (state.showLanguageDialog) {
            LanguageDialog(state.language, onAction)
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
            style = SnapdexTheme.typography.largeLabel
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(SnapdexTheme.colorScheme.surface.copy(alpha = 0.3f))
        ) {
            SettingsButton(
                text = stringResource(id = R.string.change_name),
                onClick = { onAction(ProfileAction.OnChangeNameClick) }
            )

            SnapdexHorizontalDivider()

            SettingsButton(
                text = stringResource(id = R.string.change_password),
                onClick = { onAction(ProfileAction.OnChangePasswordClick) }
            )

            SnapdexHorizontalDivider()

            SettingsButton(
                text = stringResource(id = R.string.reset_progress),
                onClick = { onAction(ProfileAction.OnResetProgressClick) }
            )

            SnapdexHorizontalDivider()

            DestructiveSettingsButton(
                text = stringResource(id = R.string.delete_account),
                enabled = !state.isDeletingAccount,
                onClick = { onAction(ProfileAction.OnDeleteAccountClick) }
            )
        }
    }
}

@Composable
private fun AppSettings(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val localeName = remember(state.language) {
        state.language.getDisplayLanguage(state.language).replaceFirstChar { it.uppercase() }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.app_settings),
            style = SnapdexTheme.typography.largeLabel
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(SnapdexTheme.colorScheme.surface.copy(alpha = 0.3f))
        ) {
            SettingsPickerButton(
                text = stringResource(id = R.string.ai_model),
                value = when (state.aiModel) {
                    AIModel.EMBEDDED -> stringResource(id = R.string.on_device)
                    AIModel.OPENAI -> stringResource(id = R.string.openai)
                },
                onClick = { onAction(ProfileAction.OnChangeAIModelClick) }
            )

            SnapdexHorizontalDivider()

            SettingsPickerButton(
                text = stringResource(id = R.string.language),
                value = localeName,
                onClick = { onAction(ProfileAction.OnChangeLanguageClick) }
            )

            SnapdexHorizontalDivider()

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
            style = SnapdexTheme.typography.largeLabel
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(SnapdexTheme.colorScheme.surface.copy(alpha = 0.3f))
        ) {
            SettingsButton(
                text = stringResource(id = R.string.licenses_and_credits),
                onClick = { onAction(ProfileAction.OnCreditsClick) }
            )

            SnapdexHorizontalDivider()

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
                color = SnapdexTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        )

    Text(
        text = buildAnnotatedString {
            append(stringResource(id = R.string.built_in_the_open))
            withLink(LinkAnnotation.Url("https://github.com/TimLariviere/Snapdex-Compose", styles)) {
                append(stringResource(id = R.string.snapdex_on_github))
            }
            append(stringResource(id = R.string.get_in_touch))
            withLink(LinkAnnotation.Url("https://timothelariviere.com", styles)) {
                append(stringResource(id = R.string.website_link))
            }
        },
        style = SnapdexTheme.typography.smallLabel,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
private fun ProgressResetConfirmationDialog(onAction: (ProfileAction) -> Unit) {
    SnapdexPopup(
        title = stringResource(id = R.string.progress_reset),
        description = stringResource(id = R.string.progress_reset_description),
        onDismissRequest = { onAction(ProfileAction.OnProgressResetCancel) },
        primaryButton = PopupButton(
            text = stringResource(id = R.string.cancel),
            onClick = { onAction(ProfileAction.OnProgressResetCancel) }
        ),
        secondaryButton = PopupButton(
            text = stringResource(id = R.string.reset),
            onClick = { onAction(ProfileAction.OnProgressResetConfirm) }
        )
    )
}

@Composable
private fun AccountDeletionConfirmationDialog(onAction: (ProfileAction) -> Unit) {
    SnapdexPopup(
        title = stringResource(id = R.string.account_deletion),
        description = stringResource(id = R.string.account_deletion_description),
        onDismissRequest = { onAction(ProfileAction.OnAccountDeletionCancel) },
        primaryButton = PopupButton(
            text = stringResource(id = R.string.cancel),
            onClick = { onAction(ProfileAction.OnAccountDeletionCancel) }
        ),
        secondaryButton = PopupButton(
            text = stringResource(id = R.string.delete),
            onClick = { onAction(ProfileAction.OnAccountDeletionConfirm) }
        )
    )
}

@Composable
private fun LanguageDialog(
    locale: Locale,
    onAction: (ProfileAction) -> Unit
) {
    SnapdexDialogPicker(
        title = stringResource(id = R.string.set_language),
        buttonText = stringResource(id = R.string.choose),
        items = listOf(
            Locale.ENGLISH,
            Locale.FRENCH
        ),
        initialItemSelected = locale,
        onItemSelect = { locale -> onAction(ProfileAction.OnLanguageChange(locale)) },
        onDismissRequest = { onAction(ProfileAction.OnLanguageDialogDismiss) },
    ) { locale ->
        val localeName = remember {
            locale.getDisplayLanguage(locale).replaceFirstChar { it.uppercase() }
        }

        Text(
            text = localeName,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        SnapdexBackground {
            ProfileScreen(
                paddingValues = PaddingValues(0.dp),
                state = ProfileState(
                    user = User(
                        id = "",
                        avatarId = 4,
                        name = "Roger",
                        email = "roger@snapdex.com"
                    ),
                    language = Locale.FRENCH,
                    showLanguageDialog = true
                ),
                onAction = {}
            )
        }
    }
}