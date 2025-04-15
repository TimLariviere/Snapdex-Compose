package com.kanoyatech.snapdex.ui.main.profile_tab.choose_aimodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.models.AIModel
import com.kanoyatech.snapdex.domain.preferences.PreferencesStore
import com.kanoyatech.snapdex.ui.utils.textAsFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChooseAIModelViewModel(private val preferences: PreferencesStore) : ViewModel() {
    var state by mutableStateOf(ChooseAIModelState())
        private set

    private val eventChannel = Channel<ChooseAIModelEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val aiModel = preferences.getAIModel()
            val apiKey =
                if (aiModel == AIModel.OPENAI) {
                    preferences.getOpenAIApiKey()
                } else {
                    ""
                }
            state =
                state.copy(
                    selected = aiModel,
                    openAIApiKey = TextFieldState(initialText = apiKey),
                    canSave = aiModel != AIModel.OPENAI || apiKey.isNotBlank(),
                )

            val selectedFlow = snapshotFlow { state.selected }

            combine(selectedFlow, state.openAIApiKey.textAsFlow()) { model, apiKey ->
                    model != null && (model != AIModel.OPENAI || apiKey.isNotBlank())
                }
                .onEach { state = state.copy(canSave = it) }
                .launchIn(viewModelScope)
        }
    }

    fun onAction(action: ChooseAIModelAction) {
        when (action) {
            is ChooseAIModelAction.OnAIModelSelect -> setAIModel(action.selected)
            ChooseAIModelAction.OnSaveClick -> save()
            else -> Unit
        }
    }

    private fun setAIModel(model: AIModel) {
        viewModelScope.launch {
            state = state.copy(selected = model)
            preferences.setAIModel(model)
        }
    }

    private fun save() {
        viewModelScope.launch {
            state.copy(isSaving = true)

            val model = state.selected ?: AIModel.EMBEDDED
            preferences.setAIModel(model)

            if (model == AIModel.OPENAI) {
                preferences.setOpenAIApiKey(state.openAIApiKey.text.toString())
            } else {
                preferences.setOpenAIApiKey("")
            }

            state.copy(isSaving = false)

            eventChannel.send(ChooseAIModelEvent.OnSaved(model))
        }
    }
}
