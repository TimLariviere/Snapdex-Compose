package com.kanoyatech.snapdex.ui

import com.kanoyatech.snapdex.ui.R

object AvatarUi {
    fun getFor(index: Int): Int {
        val imageId =
            when (index) {
                0 -> R.drawable.avatar_01
                1 -> R.drawable.avatar_02
                2 -> R.drawable.avatar_03
                3 -> R.drawable.avatar_04
                4 -> R.drawable.avatar_05
                5 -> R.drawable.avatar_06
                6 -> R.drawable.avatar_07
                7 -> R.drawable.avatar_08
                8 -> R.drawable.avatar_09
                else -> R.drawable.avatar_10
            }
        return imageId
    }
}