package com.kanoyatech.snapdex.ui.main.profile.new_name

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.designsystem.Popup
import com.kanoyatech.snapdex.theme.designsystem.PopupButton
import com.kanoyatech.snapdex.theme.designsystem.PrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTextField
import com.kanoyatech.snapdex.theme.designsystem.SnapdexTopAppBar
import com.kanoyatech.snapdex.theme.pagePadding
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewNameScreenRoot(
    viewModel: NewNameViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is NewNameEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
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
        }
    )
}

@Composable
fun NewNameScreen(
    state: NewNameState,
    onAction: (NewNameAction) -> Unit
) {
    Scaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(R.string.new_name),
                onBackClick = { onAction(NewNameAction.OnBackClick) }
            )
        }
    ) { paddingValues ->
        Box {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .pagePadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SnapdexTextField(
                    state = state.name,
                    hint = stringResource(id = R.string.new_name)
                )

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = stringResource(R.string.set_new_name),
                    onClick = { onAction(NewNameAction.OnSetNameClick) },
                    enabled = state.canChangeName,
                    isBusy = state.isChangingName,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            if (state.showNameChangedPopup) {
                Popup(
                    title = stringResource(id = R.string.name_changed),
                    description = stringResource(id = R.string.name_changed_description),
                    primaryButton = PopupButton(
                        text = stringResource(id = R.string.ok),
                        onClick = { onAction(NewNameAction.OnNameChangedPopupDismiss) }
                    ),
                    onDismissRequest = { onAction(NewNameAction.OnNameChangedPopupDismiss) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun NewNameScreenPreview() {
    AppTheme {
        NewNameScreen(
            state = NewNameState(),
            onAction = {}
        )
    }
}