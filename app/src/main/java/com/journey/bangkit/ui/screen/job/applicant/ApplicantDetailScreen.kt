package com.journey.bangkit.ui.screen.job.applicant

import android.widget.Toast
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.journey.bangkit.R
import com.journey.bangkit.data.model.Applicants
import com.journey.bangkit.ui.common.UiState
import com.journey.bangkit.ui.component.CardSkeleton
import com.journey.bangkit.ui.component.JCardApplicant
import com.journey.bangkit.ui.component.shimmerEffect
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.DarkGray80
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.viewmodel.ApplicantDetailViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicantDetailScreen(
    viewModel: ApplicantDetailViewModel = hiltViewModel(),
    vacancyId: String,
    doBack: () -> Unit,
    isPreviousBack: NavBackStackEntry?
) {
    var loading by remember { mutableStateOf(false) }
    var resultLoading by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf(listOf<Applicants>()) }
    val context = LocalContext.current

    viewModel.response.collectAsState(initial = UiState.Loading).value.let { res ->
        when (res) {
            is UiState.Loading -> {
                viewModel.getCompanyApplicant(vacancyId)
                loading = true
            }
            is UiState.Success -> {
                data = res.data
                loading = false
            }
            is UiState.Error -> loading = false
        }
    }
    
    val resultsResponse by viewModel.result.collectAsState(initial = UiState.Loading)
    LaunchedEffect(resultsResponse) {
        resultsResponse.let { result ->
            when (result) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    resultLoading = false
                    Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()
                }
                is UiState.Error -> {
                    resultLoading = false
                    Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.job_vacancy_user))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue40,
                    titleContentColor = Light
                ),
                navigationIcon = {
                    if (isPreviousBack != null) {
                        IconButton(onClick = { doBack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "",
                                tint = Light
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (loading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    repeat(3) {
                        CardSkeleton(brush = shimmerEffect())
                    }
                }
            } else {
                if (data.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Image(
                                modifier = Modifier.size(256.dp),
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
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(data, key = { Random.nextInt() }) { vacancy ->
                            JCardApplicant(
                                name = vacancy.full_name,
                                photo = vacancy.profile_photo_url,
                                disabilityName = vacancy.disability_name,
                                appliedAt = vacancy.applied_at,
                                skillOne = vacancy.skill_one_name,
                                skillTwo = vacancy.skill_two_name,
                                onAccept = {
                                    // resultLoading = true
                                    if (it)
                                        viewModel.letAccepted(vacancy.id, vacancyId)
                                    else viewModel.letRejected(vacancy.id, vacancyId)
                                },
                                email = vacancy.email,
                                isLoading = resultLoading
                            )
                        }
                    }
                }
            }
        }
    }
}