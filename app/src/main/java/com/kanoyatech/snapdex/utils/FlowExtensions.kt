package com.kanoyatech.snapdex.utils

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun TextFieldState.textAsFlow(): Flow<CharSequence> {
    return snapshotFlow { this.text }
}

fun FirebaseAuth.currentUserAsFlow(): Flow<FirebaseUser?> {
    return callbackFlow {
        val listener = FirebaseAuth.AuthStateListener {
            trySend(it.currentUser)
        }

        this@currentUserAsFlow.addAuthStateListener(listener)

        awaitClose {
            this@currentUserAsFlow.removeAuthStateListener(listener)
        }
    }
}