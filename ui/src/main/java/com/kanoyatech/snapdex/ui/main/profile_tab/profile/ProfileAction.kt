package com.kanoyatech.snapdex.ui.main.profile_tab.profile

import com.kanoyatech.snapdex.domain.models.AIModel
import java.util.Locale

sealed interface ProfileAction {
    object OnChangeNameClick : ProfileAction

    object OnChangePasswordClick : ProfileAction

    object OnResetProgressClick : ProfileAction

    object OnProgressResetConfirm : ProfileAction

    object OnProgressResetCancel : ProfileAction

    object OnDeleteAccountClick : ProfileAction

    object OnAccountDeletionConfirm : ProfileAction

    object OnAccountDeletionCancel : ProfileAction

    object OnChangeAIModelClick : ProfileAction

    data class OnAIModelChange(val aiModel: AIModel) : ProfileAction

    object OnChangeLanguageClick : ProfileAction

    data class OnLanguageChange(val language: Locale) : ProfileAction

    object OnLanguageDialogDismiss : ProfileAction

    object OnChangeNotificationsClick : ProfileAction

    object OnCreditsClick : ProfileAction

    object OnPrivacyPolicyClick : ProfileAction

    object OnLogoutClick : ProfileAction
}
