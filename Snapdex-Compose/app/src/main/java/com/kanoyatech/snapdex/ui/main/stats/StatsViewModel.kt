package com.kanoyatech.snapdex.ui.main.stats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StatsViewModel(): ViewModel() {
    var state by mutableStateOf(StatsState())
        private set

    fun onAction(action: StatsAction) {

    }
}