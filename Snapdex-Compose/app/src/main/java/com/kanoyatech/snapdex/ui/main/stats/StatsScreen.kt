package com.kanoyatech.snapdex.ui.main.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatsScreenRoot(
    paddingValues: PaddingValues,
    viewModel: StatsViewModel = koinViewModel()
) {
    StatsScreen(
        paddingValues = paddingValues,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun StatsScreen(
    paddingValues: PaddingValues,
    state: StatsState,
    onAction: (StatsAction) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text("Stats")
    }
}

@Preview
@Composable
private fun StatsScreenPreview() {
     AppTheme {
        StatsScreen(
            paddingValues = PaddingValues(0.dp),
            state = StatsState(),
            onAction = {}
        )
    }
}