package com.journey.bangkit.ui.screen.add

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AssistWalker
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.journey.bangkit.R
import com.journey.bangkit.data.model.AddVacancy
import com.journey.bangkit.data.source.DisabilityDataSource
import com.journey.bangkit.data.source.JourneyDataSource
import com.journey.bangkit.ui.common.UiState
import com.journey.bangkit.ui.component.JDatePicker
import com.journey.bangkit.ui.component.JDropdownMenu
import com.journey.bangkit.ui.component.JTextField
import com.journey.bangkit.ui.component.JTextFieldArea
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.viewmodel.AddVacancyViewModel

@Composable
fun AddVacancyScreen(
    isPreviousBack: NavBackStackEntry?,
    doBack: () -> Unit
) {
    AddVacancyContent(
        isPreviousBack = isPreviousBack,
        doBack = doBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVacancyContent(
    modifier: Modifier = Modifier,
    isPreviousBack: NavBackStackEntry?,
    doBack: () -> Unit,
    viewModel: AddVacancyViewModel = hiltViewModel()
) {
    val disabilities = DisabilityDataSource.disabilities.map { it.name }
    val jobTypes = JourneyDataSource.jobTypes
    var skillData by rememberSaveable { mutableStateOf(listOf<String>()) }
    var disabilitySelected by rememberSaveable { mutableIntStateOf(0) }
    var jobTypeSelected by rememberSaveable { mutableIntStateOf(0) }
    var skillOneSelected by rememberSaveable { mutableIntStateOf(0) }
    var skillTwoSelected by rememberSaveable { mutableIntStateOf(0) }
    var deadline by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current

    viewModel.skills.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getSkills(context)
            }

            is UiState.Error -> {}
            is UiState.Success -> {
                val data = uiState.data
                skillData = data.map { it.name }
            }
            else -> {}
        }
    }

    val addResponse by viewModel.response.collectAsState(initial = UiState.Loading)
    LaunchedEffect(addResponse) {
        addResponse.let { response ->
            when (response) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    Toast.makeText(context, response.data.message, Toast.LENGTH_SHORT).show()
                    doBack()
                }
                is UiState.Error -> {
                    Toast.makeText(context, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.add_vacancy))
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
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    JTextField(
                        leadingIcon = Icons.Filled.Work,
                        label = stringResource(id = R.string.placement_address_placeholder),
                        placeholder = stringResource(id = R.string.placement_address_placeholder),
                        onKeyUp = { position = it },
                        textValue = position
                    )
                    JTextFieldArea(
                        title = stringResource(id = R.string.description_placeholder),
                        onKeyUp = { description = it },
                        value = description
                    )
                    JDropdownMenu(
                        label = stringResource(id = R.string.job_type_placeholder),
                        icon = Icons.Filled.Work,
                        data = jobTypes,
                        setItemSelected = {
                            jobTypeSelected = it
                        },
                        itemSelected = jobTypeSelected
                    )
                    JDropdownMenu(
                        label = stringResource(id = R.string.disability_job_placeholder),
                        icon = Icons.Filled.AssistWalker,
                        data = disabilities,
                        setItemSelected = {
                            disabilitySelected = it
                        },
                        itemSelected = disabilitySelected
                    )
                    JDropdownMenu(
                        label = stringResource(id = R.string.skill_one_placeholder),
                        icon = Icons.Filled.StarHalf,
                        data = skillData,
                        setItemSelected = {
                            skillOneSelected = it
                        },
                        itemSelected = skillOneSelected
                    )
                    JDropdownMenu(
                        label = stringResource(id = R.string.skill_two_placeholder),
                        icon = Icons.Filled.StarHalf,
                        data = skillData,
                        setItemSelected = {
                            skillTwoSelected = it
                        },
                        itemSelected = skillTwoSelected
                    )
                    JDatePicker(
                        setDate = {
                            deadline = it
                        },
                        value = deadline
                    )
                }

                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = {
                        viewModel.addVacancy(
                            data = AddVacancy(
                                placement_address = position,
                                description = description,
                                id_disability = disabilitySelected,
                                skill_one = (skillOneSelected + 1).toString(),
                                skill_two = (skillTwoSelected + 1).toString(),
                                job_type = jobTypeSelected + 1,
                                deadline_time = deadline
                            )
                        )
                    }
                ) {
                    Text(text = stringResource(id = R.string.submit))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddVacancyPreview() {
    JourneyTheme {
        AddVacancyScreen(
            isPreviousBack = null,
            doBack = {}
        )
    }
}