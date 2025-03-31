package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

data class PopupButton(
    val text: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val isBusy: Boolean = false
)

@Composable
fun SnapdexPopup(
    title: String,
    description: String,
    primaryButton: PopupButton,
    isDestructive: Boolean = false,
    secondaryButton: PopupButton? = null,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (!isDestructive) {
                    SnapdexPrimaryButton(
                        text = primaryButton.text,
                        enabled = primaryButton.enabled,
                        isBusy = primaryButton.isBusy,
                        onClick = primaryButton.onClick,
                        modifier = Modifier
                            .weight(1f)
                    )
                } else {
                    SnapdexPrimaryButton(
                        text = primaryButton.text,
                        enabled = primaryButton.enabled,
                        //isBusy = primaryButton.isBusy,
                        isDestructive = true,
                        onClick = primaryButton.onClick,
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                if (secondaryButton != null) {
                    SecondaryButton(
                        text = secondaryButton.text,
                        enabled = secondaryButton.enabled,
                        //isBusy = secondaryButton.isBusy,
                        onClick = secondaryButton.onClick,
                        modifier = Modifier
                            .weight(1f),
                    )
                }
            }
        }
    }
}