package com.journey.bangkit.ui.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.journey.bangkit.R
import com.journey.bangkit.data.source.OnBoardingDataSource
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.JourneyTheme
import kotlinx.coroutines.launch
import com.journey.bangkit.ui.component.onboarding.OnBoardingContent
import com.journey.bangkit.ui.theme.Dark
import com.journey.bangkit.viewmodel.OnBoardingViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navigateToStarted: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val data = OnBoardingDataSource.data
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { 3 }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(0.dp),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            key = null,
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                orientation = Orientation.Horizontal,
                state = pagerState
            ),
            pageContent =  {
                OnBoardingContent(
                    modifier = modifier,
                    data = data,
                    pageIndex = it
                )
            }
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter)
                .height(90.dp)
                .padding(horizontal = 16.dp)
        ) {
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.setOnBoardingIsCompleted(true)
                    }
                    navigateToStarted()
                },
                modifier = modifier.align(alignment = Alignment.CenterStart)
            ) {
                Text(
                    text = stringResource(id = R.string.skip_onboarding),
                    color = Dark.copy(0.6f),
                    fontSize = 16.sp
                )
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = data.size,
                modifier = modifier.align(alignment = Alignment.Center),
                activeColor = Blue40
            )
            IconButton(
                onClick = {
                    if (pagerState.currentPage < data.size - 1) {
                        val nextPageIndex = pagerState.currentPage + 1
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(nextPageIndex)
                        }
                    } else {
                        coroutineScope.launch {
                            viewModel.setOnBoardingIsCompleted(true)
                        }
                        navigateToStarted()
                    }
                },
                modifier = modifier
                    .align(alignment = Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = if (pagerState.currentPage == data.size - 1)
                        Icons.Outlined.Check else Icons.Outlined.ArrowForward,
                    contentDescription = null,
                    tint = Blue40,
                    modifier = modifier.size(32.dp)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun OnBoardingPreview() {
    JourneyTheme {
        OnBoardingScreen(
            navigateToStarted = {}
        )
    }
}