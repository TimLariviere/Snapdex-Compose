package com.kanoyatech.snapdex.ui.intro

sealed interface IntroAction {
    data object OnNextClick: IntroAction
    data class OnCurrentPageChange(val page: Int): IntroAction
}