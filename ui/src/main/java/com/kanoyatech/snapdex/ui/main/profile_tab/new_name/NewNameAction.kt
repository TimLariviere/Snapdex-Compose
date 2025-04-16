package com.kanoyatech.snapdex.ui.main.profile_tab.new_name

sealed interface NewNameAction {
    object OnBackClick : NewNameAction

    object OnSetNameClick : NewNameAction
}
