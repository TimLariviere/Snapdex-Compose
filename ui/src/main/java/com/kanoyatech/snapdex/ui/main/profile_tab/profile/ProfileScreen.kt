package com.kanoyatech.snapdex.ui.main.profile_tab.profile

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import com.kanoyatech.snapdex.designsystem.components.PopupButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexBackground
import com.kanoyatech.snapdex.designsystem.components.SnapdexDialogPicker
import com.kanoyatech.snapdex.designsystem.components.SnapdexHorizontalDivider
import com.kanoyatech.snapdex.designsystem.components.SnapdexPopup
import com.kanoyatech.snapdex.domain.models.AIModel
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.components.AvatarView
import com.kanoyatech.snapdex.ui.main.profile_tab.components.DestructiveSettingsButton
import com.kanoyatech.snapdex.ui.main.profile_tab.components.SettingsButton
import com.kanoyatech.snapdex.ui.main.profile_tab.components.SettingsPickerButton
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import com.kanoyatech.snapdex.ui.utils.getGlobalLocale
import java.util.Locale
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreenRoot(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = koinViewModel(),
    onLoggedOut: () -> Unit,
    onChangeNameClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onChangeAIModelClick: () -> Unit,
    onCreditsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale = configuration.getGlobalLocale()

    var showResetProgressDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    LaunchedEffect(locale) { viewModel.onAction(ProfileAction.OnLanguageChange(locale)) }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ProfileEvent.OpenResetProgressDialog -> showResetProgressDialog = true
            ProfileEvent.OpenDeleteAccountDialog -> showDeleteAccountDialog = true
            ProfileEvent.OpenLanguageDialog -> showLanguageDialog = true
            is ProfileEvent.Error -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
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
        },
    )

    if (showResetProgressDialog) {
        ResetProgressDialog(
            onCancelClick = { showResetProgressDialog = false },
            onResetClick = {
                showResetProgressDialog = false
                viewModel.onAction(ProfileAction.OnProgressResetConfirm)
            },
        )
    }

    if (showDeleteAccountDialog) {
        AccountDeletionConfirmationDialog(
            onCancelClick = { showDeleteAccountDialog = false },
            onDeleteClick = {
                showDeleteAccountDialog = false
                viewModel.onAction(ProfileAction.OnAccountDeletionConfirm)
            },
        )
    }

    if (showLanguageDialog) {
        LanguageDialog(
            locale = viewModel.state.language,
            onDismissed = { showLanguageDialog = false },
            onLanguageSelected = {
                showLanguageDialog = false
                viewModel.onAction(ProfileAction.OnLanguageChange(locale))
            },
        )
    }
}

@Composable
private fun ProfileScreen(
    paddingValues: PaddingValues,
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                AvatarView(
                    avatarId = state.user.avatarId,
                    isSelected = false,
                    modifier = Modifier.height(64.dp),
                )

                Column {
                    Text(text = state.user.name, style = SnapdexTheme.typography.heading3)
                    Text(text = state.user.email, style = SnapdexTheme.typography.largeLabel)
                }
            }

            Box(modifier = Modifier.verticalScroll(rememberScrollState()).weight(1f)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    AccountSettings(state, onAction)
                    AppSettings(state, onAction)
                    About(onAction)
                    Column(
                        modifier =
                            Modifier.clip(RoundedCornerShape(16.dp))
                                .background(SnapdexTheme.colorScheme.surface)
                    ) {
                        DestructiveSettingsButton(stringResource(id = R.string.logout)) {
                            onAction(ProfileAction.OnLogoutClick)
                        }
                    }
                }
            }

            CallToAction(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
private fun AccountSettings(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Text(
            text = stringResource(id = R.string.account_settings),
            style = SnapdexTheme.typography.largeLabel,
        )

        Column(
            modifier =
                Modifier.clip(RoundedCornerShape(16.dp))
                    .background(SnapdexTheme.colorScheme.surface)
        ) {
            SettingsButton(
                text = stringResource(id = R.string.change_name),
                onClick = { onAction(ProfileAction.OnChangeNameClick) },
            )

            SnapdexHorizontalDivider()

            SettingsButton(
                text = stringResource(id = R.string.change_password),
                onClick = { onAction(ProfileAction.OnChangePasswordClick) },
            )

            SnapdexHorizontalDivider()

            SettingsButton(
                text = stringResource(id = R.string.reset_progress),
                onClick = { onAction(ProfileAction.OnResetProgressClick) },
            )

            SnapdexHorizontalDivider()

            DestructiveSettingsButton(
                text = stringResource(id = R.string.delete_account),
                enabled = !state.isDeletingAccount,
                onClick = { onAction(ProfileAction.OnDeleteAccountClick) },
            )
        }
    }
}

@Composable
private fun AppSettings(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val localeName =
        remember(state.language) {
            state.language.getDisplayLanguage(state.language).replaceFirstChar { it.uppercase() }
        }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Text(
            text = stringResource(id = R.string.app_settings),
            style = SnapdexTheme.typography.largeLabel,
        )

        Column(
            modifier =
                Modifier.clip(RoundedCornerShape(16.dp))
                    .background(SnapdexTheme.colorScheme.surface)
        ) {
            SettingsPickerButton(
                text = stringResource(id = R.string.ai_model),
                value =
                    when (state.aiModel) {
                        AIModel.EMBEDDED -> stringResource(id = R.string.on_device)
                        AIModel.OPENAI -> stringResource(id = R.string.openai)
                    },
                onClick = { onAction(ProfileAction.OnChangeAIModelClick) },
            )

            SnapdexHorizontalDivider()

            SettingsPickerButton(
                text = stringResource(id = R.string.language),
                value = localeName,
                onClick = { onAction(ProfileAction.OnChangeLanguageClick) },
            )

            SnapdexHorizontalDivider()

            SettingsPickerButton(
                text = stringResource(id = R.string.notifications),
                value = "Disabled",
                onClick = { onAction(ProfileAction.OnChangeNotificationsClick) },
            )
        }
    }
}

@Composable
private fun About(onAction: (ProfileAction) -> Unit, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Text(text = stringResource(id = R.string.about), style = SnapdexTheme.typography.largeLabel)

        Column(
            modifier =
                Modifier.clip(RoundedCornerShape(16.dp))
                    .background(SnapdexTheme.colorScheme.surface)
        ) {
            SettingsButton(
                text = stringResource(id = R.string.licenses_and_credits),
                onClick = { onAction(ProfileAction.OnCreditsClick) },
            )

            SnapdexHorizontalDivider()

            SettingsButton(
                text = stringResource(id = R.string.privacy_policy),
                onClick = { onAction(ProfileAction.OnPrivacyPolicyClick) },
            )
        }
    }
}

@Composable
private fun CallToAction(modifier: Modifier = Modifier) {
    val styles =
        TextLinkStyles(
            style =
                SpanStyle(
                    color = SnapdexTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                )
        )

    Text(
        text =
            buildAnnotatedString {
                append(stringResource(id = R.string.built_in_the_open))
                withLink(
                    LinkAnnotation.Url("https://github.com/TimLariviere/Snapdex-Compose", styles)
                ) {
                    append(stringResource(id = R.string.snapdex_on_github))
                }
                append(stringResource(id = R.string.get_in_touch))
                withLink(LinkAnnotation.Url("https://timothelariviere.com", styles)) {
                    append(stringResource(id = R.string.website_link))
                }
            },
        style = SnapdexTheme.typography.smallLabel,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun ResetProgressDialog(onCancelClick: () -> Unit, onResetClick: () -> Unit) {
    SnapdexPopup(
        title = stringResource(id = R.string.progress_reset),
        description = stringResource(id = R.string.progress_reset_description),
        onDismissRequest = onCancelClick,
        primaryButton =
            PopupButton(text = stringResource(id = R.string.cancel), onClick = onCancelClick),
        secondaryButton =
            PopupButton(text = stringResource(id = R.string.reset), onClick = onResetClick),
    )
}

@Composable
private fun AccountDeletionConfirmationDialog(
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    SnapdexPopup(
        title = stringResource(id = R.string.account_deletion),
        description = stringResource(id = R.string.account_deletion_description),
        onDismissRequest = onCancelClick,
        primaryButton =
            PopupButton(text = stringResource(id = R.string.cancel), onClick = onCancelClick),
        secondaryButton =
            PopupButton(text = stringResource(id = R.string.delete), onClick = onDeleteClick),
    )
}

@Composable
private fun LanguageDialog(
    locale: Locale,
    onDismissed: () -> Unit,
    onLanguageSelected: (Locale) -> Unit,
) {
    SnapdexDialogPicker(
        title = stringResource(id = R.string.set_language),
        buttonText = stringResource(id = R.string.choose),
        items = listOf(Locale.ENGLISH, Locale.FRENCH),
        initialItemSelected = locale,
        onItemSelect = onLanguageSelected,
        onDismissRequest = onDismissed,
    ) { locale ->
        val localeName = remember {
            locale.getDisplayLanguage(locale).replaceFirstChar { it.uppercase() }
        }

        Text(
            text = localeName,
            textAlign = TextAlign.Center,
            color = SnapdexTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@PreviewLightDark
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        SnapdexBackground {
            ProfileScreen(
                paddingValues = PaddingValues(0.dp),
                state =
                    ProfileState(
                        user =
                            User(
                                id = "",
                                avatarId = 4,
                                name = "Roger",
                                email = "roger@snapdex.com",
                            ),
                        language = Locale.FRENCH,
                    ),
                onAction = {},
            )
        }
    }
}
