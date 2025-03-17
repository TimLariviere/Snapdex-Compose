package com.kanoyatech.snapdex

data class MainState(
    val isLoading: Boolean = false,
    val hasSeenIntro: Boolean = false,
    val isLoggedIn: Boolean = false
)