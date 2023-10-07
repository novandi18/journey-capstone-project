package com.journey.bangkit.ui.screen.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AssistWalker
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.journey.bangkit.R
import com.journey.bangkit.data.model.UserApplyStatus
import com.journey.bangkit.data.model.Vacancy
import com.journey.bangkit.data.model.VacancyDetail
import com.journey.bangkit.data.source.JourneyDataSource
import com.journey.bangkit.ui.common.UiState
import com.journey.bangkit.ui.component.shimmerEffect
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Dark
import com.journey.bangkit.ui.theme.DarkGray80
import com.journey.bangkit.ui.theme.Green
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.ui.theme.Red
import com.journey.bangkit.utils.toDate
import com.journey.bangkit.viewmodel.DetailVacancyViewModel

@Composable
fun DetailScreen(
    vacancyId: String,
    isPreviousBack: NavBackStackEntry?,
    doBack: () -> Unit,
    viewModel: DetailVacancyViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var data by remember { mutableStateOf(listOf<Pair<List<VacancyDetail>, List<UserApplyStatus>>>()) }
    var applyLoading by remember { mutableStateOf(false) }

    viewModel.vacancy.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getVacancy(vacancyId)
            }
            is UiState.Success -> {
                val vacancyData = uiState.data.first
                val statusData = uiState.data.second.data
                val result = statusData.filter { status ->
                    status.vacancy_placement_address == vacancyData.vacancy.placement_address &&
                    status.company_name == vacancyData.vacancy.company_name
                }
                data = listOf(Pair(listOf(vacancyData), result))
            }
            is UiState.Error -> {
                Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    DetailBar(
        data = if (data.isEmpty()) Pair(listOf(), listOf()) else data[0],
        isPreviousBack = isPreviousBack,
        doBack = doBack,
        doApply = {
            applyLoading = true
            viewModel.doApplyVacancy(vacancyId)
        }
    )

    val applyResponse by viewModel.response.collectAsState(initial = UiState.Loading)
    LaunchedEffect(applyResponse) {
        applyResponse.let { response ->
            when (response) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    applyLoading = false
                    Toast.makeText(context, response.data.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Error -> {
                    applyLoading = false
                    Toast.makeText(context, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBar(
    data: Pair<List<VacancyDetail>, List<UserApplyStatus>>,
    isPreviousBack: NavBackStackEntry? = null,
    doBack: () -> Unit,
    doApply: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.detail_vacancy))
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
        },
        bottomBar = {
            Surface(elevation = 12.dp) {
                BottomAppBar(
                    content = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                if (data.first.isEmpty()) {
                                    Spacer(
                                        modifier = Modifier
                                            .height(12.dp)
                                            .fillMaxWidth(.3f)
                                            .background(brush = shimmerEffect())
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .fillMaxWidth(.4f)
                                            .background(brush = shimmerEffect())
                                    )
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.apply_before),
                                        fontSize = 12.sp,
                                        color = Dark.copy(.6f)
                                    )
                                    Text(
                                        text = data.first[0].vacancy.deadline_time,
                                        fontWeight = FontWeight.Medium,
                                        color = Red
                                    )
                                }
                            }
                            if (data.first.isEmpty()) {
                                Spacer(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(40.dp)
                                        .background(brush = shimmerEffect(), shape = CircleShape)
                                )
                            } else {
                                if (data.second.isNotEmpty()) {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                        text = data.second[0].status,
                                        color = when (data.second[0].status) {
                                            "Accepted" -> Green
                                            "Rejected" -> Red
                                            else -> DarkGray80
                                        }
                                    )
                                } else {
                                    Button(
                                        onClick = { doApply() },
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.apply),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                            Icon(
                                                modifier = Modifier.size(16.dp),
                                                imageVector = Icons.Filled.Send,
                                                contentDescription = stringResource(id = R.string.apply),
                                                tint = Light
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    },
                    containerColor = Light,
                    contentPadding = PaddingValues(horizontal = 16.dp)
                )
            }
        }
    ) { paddingValues ->
        DetailContent(
            data = data.first,
            innerPadding = paddingValues
        )
    }
}

@Composable
fun DetailContent(
    data: List<VacancyDetail> = listOf(),
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (data.isEmpty()) {
                    Spacer(modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth(.6f)
                        .background(brush = shimmerEffect())
                    )
                    Spacer(modifier = Modifier
                        .height(28.dp)
                        .width(60.dp)
                        .background(brush = shimmerEffect(), shape = CircleShape)
                    )
                } else {
                    Text(
                        text = data[0].vacancy.placement_address,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier
                            .background(color = Dark.copy(alpha = .1f), shape = CircleShape)
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        text = JourneyDataSource.jobTypes[data[0].vacancy.job_type - 1],
                        fontSize = 14.sp
                    )
                }
            }
            if (data.isEmpty()) {
                Spacer(modifier = Modifier
                    .fillMaxWidth(.9f)
                    .height(12.dp)
                    .background(brush = shimmerEffect())
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(brush = shimmerEffect())
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth(.7f)
                    .height(12.dp)
                    .background(brush = shimmerEffect())
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(12.dp)
                    .background(brush = shimmerEffect())
                )
            } else {
                Text(
                    text = data[0].vacancy.description,
                    fontSize = 16.sp
                )
            }
        }
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (data.isEmpty()) {
                Spacer(
                    modifier = Modifier
                        .size(48.dp)
                        .background(brush = shimmerEffect(), shape = CircleShape)
                )
            } else {
                AsyncImage(
                    modifier = Modifier.size(48.dp),
                    model = data[0].vacancy.company_logo,
                    contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (data.isEmpty()) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(.6f)
                            .background(brush = shimmerEffect())
                    )
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(.4f)
                            .background(brush = shimmerEffect())
                    )
                } else {
                    Text(
                        text = data[0].vacancy.company_name
                    )
                    Text(
                        text = data[0].vacancy.sector_name,
                        fontSize = 14.sp
                    )
                }
            }
        }
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (data.isEmpty()) {
                Spacer(
                    modifier = Modifier
                        .size(48.dp)
                        .background(brush = shimmerEffect(), shape = CircleShape)
                )
            } else {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Filled.AssistWalker,
                    contentDescription = stringResource(id = R.string.disability),
                    tint = Blue40
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (data.isEmpty()) {
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(.3f)
                            .background(brush = shimmerEffect())
                    )
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(.7f)
                            .background(brush = shimmerEffect())
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.vacancy_apply),
                        fontSize = 12.sp,
                        color = Dark.copy(.8f)
                    )
                    Text(text = data[0].vacancy.disability_name)
                }
            }
        }
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (data.isEmpty()) {
                Spacer(
                    modifier = Modifier
                        .size(48.dp)
                        .background(brush = shimmerEffect(), shape = CircleShape)
                )
            } else {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Filled.StarHalf,
                    contentDescription = stringResource(id = R.string.disability),
                    tint = Blue40
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (data.isEmpty()) {
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(.35f)
                            .background(brush = shimmerEffect())
                    )
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(.6f)
                            .background(brush = shimmerEffect(), shape = CircleShape)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(.8f)
                            .background(brush = shimmerEffect(), shape = CircleShape)
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.skill_requiremnt),
                        fontSize = 12.sp,
                        color = Dark.copy(.8f)
                    )
                    Text(
                        modifier = Modifier
                            .background(color = Dark.copy(.1f), shape = CircleShape)
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        text = data[0].vacancy.skill_one_name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier
                            .background(color = Dark.copy(.1f), shape = CircleShape)
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        text = data[0].vacancy.skill_two_name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    JourneyTheme {
        DetailContent(
            data = listOf(VacancyDetail(
                status = "",
                vacancy = JourneyDataSource.vacancy
            )),
            innerPadding = PaddingValues()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailBarPreview() {
    JourneyTheme {
        DetailBar(
            data = Pair(listOf(), listOf()),
            doBack = {},
            doApply = {}
        )
    }
}