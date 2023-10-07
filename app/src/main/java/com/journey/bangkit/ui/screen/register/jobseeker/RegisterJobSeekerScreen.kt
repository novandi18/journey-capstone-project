package com.journey.bangkit.ui.screen.register.jobseeker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.journey.bangkit.R
import com.journey.bangkit.ui.component.auth.AuthHeader
import com.journey.bangkit.ui.component.auth.AuthTitle
import com.journey.bangkit.ui.component.auth.register.jobseeker.RegisterJobSeekerForm
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.viewmodel.RegisterJobSeekerViewModel
import com.journey.bangkit.data.model.Disability
import com.journey.bangkit.data.model.Skill
import com.journey.bangkit.data.model.UserJobSeeker
import com.journey.bangkit.data.source.DisabilityDataSource
import com.journey.bangkit.ui.common.UiState

@Composable
fun RegisterJobSeekerScreen(
    backToLogin: () -> Unit,
    viewModel: RegisterJobSeekerViewModel = hiltViewModel(),
    navigateToSkill: (UserJobSeeker) -> Unit
) {
    val context = LocalContext.current

    viewModel.disability.collectAsState(initial = UiState.Loading).value.let { uiState ->
        var isLoading by remember { mutableStateOf(false) }
        var disabilityData by mutableStateOf(listOf<Disability>())
        var skillData by mutableStateOf(listOf<Skill>())

        when (uiState) {
            is UiState.Loading -> {
                isLoading = true
                viewModel.getDisabilitySkillData(context = context)
            }
            is UiState.Error -> isLoading = false
            is UiState.Success -> {
                disabilityData = uiState.data.first
                skillData = uiState.data.second
                isLoading = false
            }
        }

        RegisterJobSeekerContent(
            backToLogin = backToLogin,
            disability = disabilityData,
            isLoading = isLoading,
            continueRegister = { data ->
                navigateToSkill(data)
            }
        )
    }
}

@Composable
fun RegisterJobSeekerContent(
    modifier: Modifier = Modifier,
    backToLogin: () -> Unit,
    disability: List<Disability>,
    isLoading: Boolean = false,
    continueRegister: (UserJobSeeker) -> Unit
) {
    Column(
        modifier = modifier.background(color = Color.White)
    ) {
        AuthHeader(back = backToLogin, title = stringResource(id = R.string.jobseeker))
        if (isLoading) {
            LinearProgressIndicator(
                modifier = modifier.fillMaxWidth(),
                color = Blue40
            )
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AuthTitle(
                title = stringResource(id = R.string.register_title),
                description = stringResource(id = R.string.register_description)
            )
            RegisterJobSeekerForm(
                disabilityData = disability,
                continueRegister = continueRegister
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterJobSeekerPreview() {
    JourneyTheme {
        RegisterJobSeekerContent(
            backToLogin = {},
            disability = DisabilityDataSource.disabilities,
            continueRegister = {}
        )
    }
}