package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import java.util.Locale

@Composable
fun <T> SnapdexDialogPicker(
    title: String,
    buttonText: String,
    items: List<T>,
    initialItemSelected: T,
    onItemSelect: (T) -> Unit,
    onDismissRequest: () -> Unit,
    itemContent: @Composable (T) -> Unit,
) {
    val selected = remember { mutableIntStateOf(items.indexOf(initialItemSelected)) }

    Dialog(onDismissRequest) {
        CompositionLocalProvider(LocalContentColor provides SnapdexTheme.colorScheme.onBackground) {
            Column(
                modifier =
                    Modifier.clip(SnapdexTheme.shapes.small)
                        .background(SnapdexTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = title,
                    style = SnapdexTheme.typography.heading3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items.forEachIndexed { index, item ->
                        val isSelected = index == selected.intValue

                        Box(
                            modifier =
                                Modifier.weight(1f)
                                    .clip(SnapdexTheme.shapes.regular)
                                    .background(SnapdexTheme.colorScheme.surface)
                                    .border(
                                        width = if (isSelected) 1.dp else 0.dp,
                                        color =
                                            if (isSelected) {
                                                SnapdexTheme.colorScheme.primary
                                            } else {
                                                SnapdexTheme.colorScheme.outline
                                            },
                                        shape = SnapdexTheme.shapes.regular,
                                    )
                                    .clickable { selected.intValue = index }
                        ) {
                            Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                                itemContent(item)
                            }
                        }
                    }
                }

                SnapdexPrimaryButton(text = buttonText, modifier = Modifier.fillMaxWidth()) {
                    onItemSelect(items[selected.intValue])
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SnapdexDialogPickerPreview() {
    AppTheme {
        SnapdexBackground {
            SnapdexDialogPicker(
                title = "Set language",
                buttonText = "Set",
                items = listOf(Locale.ENGLISH, Locale.FRENCH),
                initialItemSelected = Locale.ENGLISH,
                onItemSelect = {},
                onDismissRequest = {},
            ) { locale ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = locale.getDisplayLanguage(locale).replaceFirstChar { it.uppercase() },
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
