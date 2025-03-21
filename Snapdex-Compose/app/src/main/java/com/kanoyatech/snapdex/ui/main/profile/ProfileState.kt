package com.kanoyatech.snapdex.ui.main.profile

import com.kanoyatech.snapdex.domain.models.User
import java.util.Locale

data class ProfileState(
    val user: User = User(id = null, avatarId = 0, name = "", email = ""),
    val language: Locale = Locale.ENGLISH,
    val showProgressResetDialog: Boolean = false,
    val showAccountDeletionDialog: Boolean = false
)
