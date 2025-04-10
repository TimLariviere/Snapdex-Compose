package com.kanoyatech.snapdex.ui.intro

sealed interface IntroEvent {
    object PreferencesUpdated : IntroEvent

    data class PageChanged(val page: Int) : IntroEvent
}
