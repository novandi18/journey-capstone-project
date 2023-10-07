package com.journey.bangkit.ui.screen.home.jobseeker

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.journey.bangkit.data.source.JourneyDataSource
import com.journey.bangkit.ui.component.home.jobseeker.JSearch
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.viewmodel.HomeJobSeekerViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.journey.bangkit.data.model.AllVacancy
import com.journey.bangkit.ui.common.UiState
import com.journey.bangkit.ui.component.CardSkeleton
import com.journey.bangkit.ui.component.JCard
import com.journey.bangkit.ui.component.shimmerEffect
import com.journey.bangkit.utils.toDate
import kotlinx.coroutines.launch

@Composable
fun HomeJobSeekerScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeJobSeekerViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    setSearchActive: (Boolean) -> Unit,
    isSearchActive: Boolean
) {
    val jobTypes = JourneyDataSource.jobTypes
    var categorySelected by remember { mutableIntStateOf(1) }
    val categories = JourneyDataSource.navigationCategory
    val scope = rememberCoroutineScope()
    var data by remember { mutableStateOf(listOf<AllVacancy>()) }
    val recommended by viewModel.predict.collectAsState(initial = UiState.Loading)
    val vacancies = viewModel.vacancies.collectAsLazyPagingItems()
    val context = LocalContext.current

    LaunchedEffect(categorySelected) {
        if (categorySelected != 4) {
            data = listOf()
            vacancies.refresh()
        } else {
            recommended.let { response ->
                when (response) {
                    is UiState.Loading -> viewModel.getPredict()
                    is UiState.Success -> {
                        val dataFiltered = response.data.second.filter { x ->
                            x.placement_address in response.data.first
                        }
                        data = dataFiltered.ifEmpty { response.data.second }
                    }
                    is UiState.Error -> {
                        Toast.makeText(context, response.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Blue40)
        ) {
            JSearch(
                active = isSearchActive,
                setActive = { setSearchActive(it) },
                viewModel = viewModel,
                navigateToDetail = navigateToDetail
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = Blue40)
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEachIndexed { index, category ->
                    TextButton(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (index + 1 == categorySelected) Color.White else Color.White.copy(.2f)
                        ),
                        onClick = {
                            categorySelected = index + 1
                            scope.launch {
                                viewModel.setVacancyCategory(index + 1)
                            }
                        },
                    ) {
                        Text(
                            text = stringResource(id = category),
                            color = if (index + 1 == categorySelected) Blue40 else Color.White
                        )
                    }
                }
            }
        }

        if (data.isNotEmpty() || categorySelected == 4) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(count = data.size) { index ->
                    val vacancy = data[index]
                    JCard(
                        title = vacancy.placement_address,
                        imageUrl = vacancy.company_logo,
                        jobType = jobTypes[vacancy.job_type - 1],
                        disabilityType = vacancy.disability_name,
                        skillOne = vacancy.skill_one_name,
                        skillTwo = vacancy.skill_two_name,
                        deadline = vacancy.deadline_time.toDate(),
                        description = vacancy.description,
                        setClick = navigateToDetail,
                        id = vacancy.id
                    )
                }
            }
        } else {
            if (vacancies.loadState.refresh == LoadState.Loading) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    repeat(5) {
                        CardSkeleton(brush = shimmerEffect())
                    }
                }
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(count = vacancies.itemCount) { index ->
                        val vacancy = vacancies[index]
                        if (vacancy != null && vacancy.job_type <= 3) {
                            JCard(
                                title = vacancy.placement_address,
                                imageUrl = vacancy.company_logo,
                                jobType = jobTypes[vacancy.job_type - 1],
                                disabilityType = vacancy.disability_name,
                                skillOne = vacancy.skill_one_name,
                                skillTwo = vacancy.skill_two_name,
                                deadline = vacancy.deadline_time.toDate(),
                                description = vacancy.description,
                                setClick = navigateToDetail,
                                id = vacancy.id
                            )
                        }
                    }

                    item {
                        if (vacancies.loadState.append is LoadState.Loading) {
                            repeat(5) {
                                CardSkeleton(brush = shimmerEffect())
                            }
                        }
                    }
                }
            }
        }

    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun HomeJobSeekerPreview() {
    JourneyTheme {
        HomeJobSeekerScreen(
            navigateToDetail = {},
            setSearchActive = {},
            isSearchActive = false
        )
    }
}