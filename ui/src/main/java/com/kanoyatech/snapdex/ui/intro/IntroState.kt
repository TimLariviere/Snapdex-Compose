package com.kanoyatech.snapdex.ui.intro

data class IntroState(val currentPage: Int = 0) {
    companion object {
        const val TOTAL_PAGE_COUNT = 3
    }
}
