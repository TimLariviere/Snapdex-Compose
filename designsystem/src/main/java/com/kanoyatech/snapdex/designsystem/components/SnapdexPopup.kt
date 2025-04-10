package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

data class PopupButton(
    val text: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val isBusy: Boolean = false,
)

@Composable
fun SnapdexPopup(
    title: String,
    description: String,
    primaryButton: PopupButton,
    secondaryButton: PopupButton? = null,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest) {
        AppTheme {
            Column(
                modifier =
                    Modifier.clip(SnapdexTheme.shapes.small)
                        .background(SnapdexTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Text(
                    text = title,
                    style = SnapdexTheme.typography.heading3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SnapdexPrimaryButton(
                        text = primaryButton.text,
                        enabled = primaryButton.enabled,
                        // isBusy = primaryButton.isBusy,
                        onClick = primaryButton.onClick,
                        modifier = Modifier.weight(1f),
                    )

                    if (secondaryButton != null) {
                        SnapdexSecondaryButton(
                            text = secondaryButton.text,
                            enabled = secondaryButton.enabled,
                            // isBusy = secondaryButton.isBusy,
                            onClick = secondaryButton.onClick,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}
