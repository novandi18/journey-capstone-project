package com.journey.bangkit.ui.component.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journey.bangkit.data.model.OnBoarding
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Dark

@Composable
fun OnBoardingContent(
    modifier: Modifier = Modifier,
    data: List<OnBoarding>,
    pageIndex: Int
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 90.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(38.dp),
            modifier = modifier.padding(42.dp)
        ) {
            Image(
                painter = painterResource(id = data[pageIndex].image),
                contentDescription = stringResource(id = data[pageIndex].title),
                modifier = modifier.size(200.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = data[pageIndex].title),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue40,
                    modifier = modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(id = data[pageIndex].description),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Dark.copy(alpha = 0.8f)
                )
            }
        }
    }
}