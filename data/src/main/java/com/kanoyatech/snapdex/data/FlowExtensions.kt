package com.kanoyatech.snapdex.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun FirebaseAuth.currentUserAsFlow(): Flow<FirebaseUser?> {
    return callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { trySend(it.currentUser) }

        this@currentUserAsFlow.addAuthStateListener(listener)

        awaitClose { this@currentUserAsFlow.removeAuthStateListener(listener) }
    }
}
