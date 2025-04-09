package com.kanoyatech.snapdex.ui.main.profile.new_name

sealed interface NewNameAction {
    object OnBackClick: NewNameAction
    object OnSetNameClick: NewNameAction
    object OnNameChangedPopupDismiss: NewNameAction
}