package com.journey.bangkit.ui.component.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journey.bangkit.R
import com.journey.bangkit.ui.theme.Blue40

@Composable
fun AuthHeader(
    modifier: Modifier = Modifier,
    back: () -> Unit,
    title: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 8.dp, start = 12.dp, end = 12.dp)
    ) {
        IconButton(
            onClick = { back() },
            modifier = modifier.align(alignment = Alignment.CenterStart)
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
        Text(
            text = title,
            modifier = modifier.align(alignment = Alignment.Center),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Box(
            modifier = modifier
                .height(28.dp)
                .width(28.dp)
                .align(alignment = Alignment.CenterEnd)
                .background(color = Blue40, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = modifier.size(18.dp)
            )
        }
    }
}