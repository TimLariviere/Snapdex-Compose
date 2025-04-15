package com.kanoyatech.snapdex.ui.main.profile_tab.choose_aimodel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.components.SnapdexPrimaryButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexRadioButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexScaffold
import com.kanoyatech.snapdex.designsystem.components.SnapdexTextField
import com.kanoyatech.snapdex.designsystem.components.SnapdexTopAppBar
import com.kanoyatech.snapdex.designsystem.pagePadding
import com.kanoyatech.snapdex.domain.models.AIModel
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChooseAIModelScreenRoot(
    viewModel: ChooseAIModelViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onSaved: (AIModel) -> Unit,
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ChooseAIModelEvent.OnSaved -> onSaved(event.model)
        }
    }

    ChooseAIModelScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ChooseAIModelAction.OnBackClick -> onBackClick()
                else -> Unit
            }

            viewModel.onAction(action)
        },
    )
}

@Composable
private fun ChooseAIModelScreen(
    state: ChooseAIModelState,
    onAction: (ChooseAIModelAction) -> Unit,
) {
    val options = listOf(AIModel.EMBEDDED, AIModel.OPENAI)

    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(id = R.string.choose_ai_model),
                onBackClick = { onAction(ChooseAIModelAction.OnBackClick) },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).pagePadding().fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.widthIn(max = 460.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                options.forEach { model ->
                    Row(
                        modifier =
                            Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = { onAction(ChooseAIModelAction.OnAIModelSelect(model)) },
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        SnapdexRadioButton(selected = model == state.selected)

                        Text(
                            text =
                                when (model) {
                                    AIModel.EMBEDDED -> stringResource(id = R.string.on_device)
                                    AIModel.OPENAI -> stringResource(id = R.string.openai)
                                }
                        )
                    }
                }
            }

            if (state.selected == AIModel.OPENAI) {
                SnapdexTextField(
                    state = state.openAIApiKey,
                    hint = stringResource(id = R.string.openai_api_key),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            SnapdexPrimaryButton(
                text = stringResource(id = R.string.save),
                onClick = { onAction(ChooseAIModelAction.OnSaveClick) },
                enabled = state.canSave,
                isBusy = state.isSaving,
            )
        }
    }
}

@Preview
@Composable
private fun ChooseAIModelScreenPreview() {
    AppTheme {
        ChooseAIModelScreen(state = ChooseAIModelState(selected = AIModel.OPENAI), onAction = {})
    }
}
