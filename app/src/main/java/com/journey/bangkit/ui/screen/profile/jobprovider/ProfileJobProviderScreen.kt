package com.journey.bangkit.ui.screen.profile.jobprovider

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.journey.bangkit.R
import com.journey.bangkit.data.model.ProfileCompany
import com.journey.bangkit.ui.common.UiState
import com.journey.bangkit.ui.component.shimmerEffect
import com.journey.bangkit.ui.component.skeleton.ProfileJobSeekerSkeleton
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Dark
import com.journey.bangkit.ui.theme.DarkGray80
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.ui.theme.Red
import com.journey.bangkit.viewmodel.ProfileJobProviderViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileJobProviderScreen(
    viewModel: ProfileJobProviderViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var data by remember { mutableStateOf(ProfileCompany()) }

    viewModel.company.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                isLoading = true
                viewModel.getCompany()
            }
            is UiState.Success -> {
                data = uiState.data
                isLoading = false
            }
            is UiState.Error -> isLoading = false
        }
    }
    ProfileJobProviderContent(
        isLoading = isLoading,
        data = data,
        logout = {
            scope.launch {
                viewModel.logoutUser()
                navigateToLogin()
            }
        }
    )
}

@Composable
fun ProfileJobProviderContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    data: ProfileCompany,
    logout: () -> Unit
) {
    if (isLoading) {
        ProfileJobSeekerSkeleton(brush = shimmerEffect())
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Blue40)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .background(color = Light, shape = CircleShape)
                        .padding(30.dp)
                        .size(100.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.profile_photo),
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = data.name.toString(),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = data.sector_name.toString(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        modifier = modifier
                            .size(32.dp)
                            .align(alignment = Alignment.CenterVertically),
                        imageVector = Icons.Filled.Mail,
                        contentDescription = stringResource(id = R.string.email),
                        tint = DarkGray80
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.email),
                            fontSize = 12.sp,
                            color = DarkGray80
                        )
                        Text(
                            text = data.email.toString()
                        )
                    }
                }

                Divider(
                    color = Dark.copy(alpha = .1f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        modifier = modifier
                            .size(32.dp)
                            .align(alignment = Alignment.CenterVertically),
                        imageVector = Icons.Filled.LocationCity,
                        contentDescription = stringResource(id = R.string.address),
                        tint = DarkGray80
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.address),
                            fontSize = 12.sp,
                            color = DarkGray80
                        )
                        Text(
                            text = data.address.toString()
                        )
                    }
                }

                Divider(
                    color = Dark.copy(alpha = .1f)
                )
            }

            Divider(
                color = Dark.copy(alpha = .1f)
            )

            TextButton(
                modifier = modifier.fillMaxWidth(),
                onClick = { logout() }
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        modifier = modifier
                            .size(24.dp)
                            .align(alignment = Alignment.CenterVertically),
                        imageVector = Icons.Filled.Logout,
                        contentDescription = stringResource(id = R.string.logout),
                        tint = Red
                    )
                    Text(
                        text = stringResource(id = R.string.logout),
                        color = Red
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileJobProviderPreview() {
    JourneyTheme {
        ProfileJobProviderContent(
            isLoading = false,
            data = ProfileCompany(),
            logout = {}
        )
    }
}