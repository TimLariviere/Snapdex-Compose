package com.kanoyatech.snapdex.ui.intro

data class IntroState(
    val currentPage: Int = 0
) {
    companion object {
        val TOTAL_PAGE_COUNT = 3
    }
}
