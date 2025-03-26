package com.kanoyatech.snapdex.ui.main

sealed interface MainAction {
    object OnLoggedOut : MainAction
}
