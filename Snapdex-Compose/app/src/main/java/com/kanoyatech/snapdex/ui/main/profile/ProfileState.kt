package com.kanoyatech.snapdex.ui.main.profile

import com.kanoyatech.snapdex.domain.models.User

data class ProfileState(
    val user: User = User(id = null, avatarId = 0, name = "", email = ""),
    val showProgressResetDialog: Boolean = false,
    val showAccountDeletionDialog: Boolean = false
)
