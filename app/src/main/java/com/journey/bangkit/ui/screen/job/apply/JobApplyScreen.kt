package com.journey.bangkit.ui.screen.job.apply

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.journey.bangkit.R
import com.journey.bangkit.data.model.UserApplyStatus
import com.journey.bangkit.data.model.UserApplyStatusResponse
import com.journey.bangkit.ui.common.UiState
import com.journey.bangkit.ui.component.CardSkeleton
import com.journey.bangkit.ui.component.JCardApply
import com.journey.bangkit.ui.component.shimmerEffect
import com.journey.bangkit.ui.theme.DarkGray80
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.utils.toDate
import com.journey.bangkit.viewmodel.JobApplyViewModel
import kotlin.random.Random

@Composable
fun JobApplyScreen(
    viewModel: JobApplyViewModel = hiltViewModel()
) {
    var isLoading by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf(listOf<UserApplyStatusResponse>()) }

    viewModel.response.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getJobApply()
                isLoading = true
            }
            is UiState.Success -> {
                data = listOf(uiState.data)
                isLoading = false
            }
            is UiState.Error -> {
                isLoading = false
            }
        }

        JobApplyContent(data = if (data.isEmpty()) listOf() else data[0].data, isLoading = isLoading)
    }
}

@Composable
fun JobApplyContent(
    modifier: Modifier = Modifier,
    data: List<UserApplyStatus>,
    isLoading: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoading) {
            repeat(3) {
                CardSkeleton(brush = shimmerEffect())
            }
        } else {
            if (data.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Image(
                            modifier = modifier.size(256.dp),
                            painter = painterResource(id = R.drawable.job_apply_empty),
                            contentDescription = stringResource(id = R.string.job_apply_empty)
                        )
                        Text(
                            text = stringResource(id = R.string.job_apply_empty),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkGray80
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(data, key = { Random.nextInt() }) { job ->
                        JCardApply(
                            position = job.vacancy_placement_address,
                            company = job.company_name,
                            skill_one = job.skill_one_name,
                            skill_two = job.skill_two_name,
                            disability = job.disability_name,
                            appliedAt = job.applied_at.toDate(),
                            status = job.status,
                            onClick = {},
                            id = job.id
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobApplyPreview() {
    JourneyTheme {
        JobApplyContent(
            data = listOf(),
            isLoading = false
        )
    }
}