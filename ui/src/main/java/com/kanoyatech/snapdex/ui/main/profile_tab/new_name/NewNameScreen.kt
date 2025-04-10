package com.kanoyatech.snapdex.ui.main.profile_tab.new_name

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.components.PopupButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexPopup
import com.kanoyatech.snapdex.designsystem.components.SnapdexPrimaryButton
import com.kanoyatech.snapdex.designsystem.components.SnapdexScaffold
import com.kanoyatech.snapdex.designsystem.components.SnapdexTextField
import com.kanoyatech.snapdex.designsystem.components.SnapdexTopAppBar
import com.kanoyatech.snapdex.designsystem.pagePadding
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewNameScreenRoot(viewModel: NewNameViewModel = koinViewModel(), onBackClick: () -> Unit) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is NewNameEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }
            NewNameEvent.NameChanged -> {
                keyboardController?.hide()
            }
        }
    }

    NewNameScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                NewNameAction.OnBackClick -> onBackClick()
                NewNameAction.OnNameChangedPopupDismiss -> onBackClick()
                else -> Unit
            }

            viewModel.onAction(action)
        },
    )
}

@Composable
fun NewNameScreen(state: NewNameState, onAction: (NewNameAction) -> Unit) {
    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(R.string.new_name),
                onBackClick = { onAction(NewNameAction.OnBackClick) },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).pagePadding().fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SnapdexTextField(state = state.name, hint = stringResource(id = R.string.new_name))

            Spacer(modifier = Modifier.weight(1f))

            SnapdexPrimaryButton(
                text = stringResource(R.string.set_new_name),
                onClick = { onAction(NewNameAction.OnSetNameClick) },
                enabled = state.canChangeName,
                isBusy = state.isChangingName,
            )
        }

        if (state.showNameChangedPopup) {
            SnapdexPopup(
                title = stringResource(id = R.string.name_changed),
                description = stringResource(id = R.string.name_changed_description),
                primaryButton =
                    PopupButton(
                        text = stringResource(id = R.string.ok),
                        onClick = { onAction(NewNameAction.OnNameChangedPopupDismiss) },
                    ),
                onDismissRequest = { onAction(NewNameAction.OnNameChangedPopupDismiss) },
            )
        }
    }
}

@Preview
@Composable
private fun NewNameScreenPreview() {
    AppTheme { NewNameScreen(state = NewNameState(), onAction = {}) }
}
