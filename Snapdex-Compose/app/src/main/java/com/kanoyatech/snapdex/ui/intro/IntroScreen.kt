package com.kanoyatech.snapdex.ui.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.designsystem.PrimaryButton
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun IntroScreenRoot(
    viewModel: IntroViewModel = koinViewModel(),
    onContinueClick: () -> Unit
) {
    val animationScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = viewModel.state.currentPage) { IntroState.TOTAL_PAGE_COUNT }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setCurrentPage(pagerState.currentPage)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            IntroEvent.PreferencesUpdated -> onContinueClick()
            is IntroEvent.PageChanged -> {
                animationScope.launch {
                    pagerState.animateScrollToPage(event.page)
                }
            }
        }
    }

    IntroScreen(
        pagerState = pagerState,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
fun IntroScreen(
    pagerState: PagerState,
    state: IntroState,
    onAction: (IntroAction) -> Unit,
) {
    val isLastPage = state.currentPage == IntroState.TOTAL_PAGE_COUNT - 1

    Column(
        verticalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            val image: Int
            val description: Int

            when (index) {
                0 -> {
                    image = R.mipmap.intro_1
                    description = R.string.intro_description_1
                }
                1 -> {
                    image = R.mipmap.intro_2
                    description = R.string.intro_description_2
                }
                else -> {
                    image = R.mipmap.intro_3
                    description = R.string.intro_description_3
                }
            }

            Column {
                Image(
                    bitmap = ImageBitmap.imageResource(id = image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Text(
                    text = stringResource(id = description),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            repeat(IntroState.TOTAL_PAGE_COUNT) { iteration ->
                val color = if (state.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }

        PrimaryButton(
            text = if (!isLastPage) {
                stringResource(id = R.string.next)
            } else {
                stringResource(id = R.string.gotta_snapem_all)
            },
            onClick = {
                onAction(IntroAction.OnNextClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IntroScreenPreview() {
    AppTheme {
        IntroScreen(
            pagerState = PagerState { 3 },
            state = IntroState(),
            onAction = {}
        )
    }
}