package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme
import java.util.Locale

@Composable
fun <T> SnapdexDialogPicker(
    title: String,
    buttonText: String,
    items: List<T>,
    initialItemSelected: T,
    onItemSelect: (T) -> Unit,
    onDismissRequest: () -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    val selected = remember { mutableIntStateOf(items.indexOf(initialItemSelected)) }

    Dialog(onDismissRequest) {
        CompositionLocalProvider(
            LocalContentColor provides SnapdexTheme.colorScheme.onSurface
        ) {
            Column(
                modifier = Modifier
                    .clip(SnapdexTheme.shapes.regular)
                    .background(SnapdexTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = SnapdexTheme.typography.heading3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                items.forEachIndexed { index, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(SnapdexTheme.shapes.small)
                            .background(Color.White)
                            .border(
                                width = if (index == selected.intValue) 4.dp else 1.dp,
                                color = if (index == selected.intValue) {
                                    SnapdexTheme.colorScheme.primary
                                } else {
                                    SnapdexTheme.colorScheme.outline
                                },
                                shape = SnapdexTheme.shapes.small
                            )
                            .clickable {
                                selected.intValue = index
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            itemContent(item)
                        }
                    }
                }

                SnapdexPrimaryButton(
                    text = buttonText,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    onItemSelect(items[selected.intValue])
                }
            }
        }
    }
}

@Preview
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
                onDismissRequest = {}
            ) { locale ->
                Text(locale.getDisplayLanguage(locale).replaceFirstChar { it.uppercase() })
            }
        }
    }
}