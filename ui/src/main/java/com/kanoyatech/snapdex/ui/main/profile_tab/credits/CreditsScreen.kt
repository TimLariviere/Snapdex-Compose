package com.kanoyatech.snapdex.ui.main.profile_tab.credits

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.components.SnapdexScaffold
import com.kanoyatech.snapdex.designsystem.components.SnapdexTopAppBar
import com.kanoyatech.snapdex.designsystem.pagePadding
import com.kanoyatech.snapdex.ui.R
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun CreditsScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val markdown = remember {
        context.resources.openRawResource(R.raw.credits).bufferedReader().use { it.readText() }
    }

    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = stringResource(id = R.string.licenses_and_credits),
                onBackClick = onBackClick,
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Box(modifier = Modifier.verticalScroll(rememberScrollState()).clipToBounds()) {
                MarkdownText(markdown = markdown, modifier = Modifier.Companion.pagePadding())
            }
        }
    }
}

@Preview
@Composable
private fun CreditsScreenPreview() {
    AppTheme { CreditsScreen(onBackClick = {}) }
}
