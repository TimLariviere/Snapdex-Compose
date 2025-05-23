package com.kanoyatech.snapdex.ui.main.profile_tab.profile

import com.kanoyatech.snapdex.domain.models.AIModel
import com.kanoyatech.snapdex.domain.models.User
import java.util.Locale

data class ProfileState(
    val user: User = User(id = null, avatarId = 0, name = "", email = ""),
    val language: Locale = Locale.ENGLISH,
    val aiModel: AIModel = AIModel.EMBEDDED,
    val isDeletingAccount: Boolean = false,
)
