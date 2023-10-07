package com.journey.bangkit.ui.screen.started

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journey.bangkit.R
import com.journey.bangkit.ui.component.started.BottomButton
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Blue80
import com.journey.bangkit.ui.theme.JourneyTheme

@Composable
fun StartedScreen(
    modifier: Modifier = Modifier,
    navigateToJobSeeker: () -> Unit,
    navigateToJobProvider: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Blue40, Blue80)
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.auth_title),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 38.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.img_auth),
            contentDescription = null,
            modifier = modifier
                .size(300.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        BottomButton(
            modifier = modifier,
            navigateToJobSeeker = navigateToJobSeeker,
            navigateToJobProvider = navigateToJobProvider
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StartedPreview() {
    JourneyTheme {
        StartedScreen(
            navigateToJobSeeker = {},
            navigateToJobProvider = {}
        )
    }
}