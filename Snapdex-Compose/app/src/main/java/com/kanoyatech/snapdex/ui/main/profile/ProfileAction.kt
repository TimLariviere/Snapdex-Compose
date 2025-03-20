package com.kanoyatech.snapdex.ui.main.profile

sealed interface ProfileAction {
    data object OnLogoutClick : ProfileAction
    data object OnDeleteAccountClick : ProfileAction
    data object OnAccountDeletionConfirm: ProfileAction
    data object OnAccountDeletionCancel: ProfileAction
}