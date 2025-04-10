package com.kanoyatech.snapdex

data class MainActivityState(
    val isLoading: Boolean = false,
    val hasSeenIntro: Boolean = false,
    val isLoggedIn: Boolean = false,
)
