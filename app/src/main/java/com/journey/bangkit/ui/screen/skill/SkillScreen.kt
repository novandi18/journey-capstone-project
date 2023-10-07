package com.journey.bangkit.ui.screen.skill

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.journey.bangkit.R
import com.journey.bangkit.data.model.Skill
import com.journey.bangkit.data.model.UserJobSeeker
import com.journey.bangkit.ui.common.UiState
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Blue80
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.viewmodel.RegisterJobSeekerViewModel

@Composable
fun SkillScreen(
    dataFromRegister: UserJobSeeker?,
    viewModel: RegisterJobSeekerViewModel = hiltViewModel(),
    backToLogin: () -> Unit
) {
    val context = LocalContext.current
    var registerLoading by remember { mutableStateOf(false) }

    viewModel.disability.collectAsState(initial = UiState.Loading).value.let { uiState ->
        var skillData by mutableStateOf(listOf<Skill>())

        when (uiState) {
            is UiState.Loading -> {
                viewModel.getDisabilitySkillData(context)
            }
            is UiState.Success -> {
                skillData = uiState.data.second
            }
            is UiState.Error -> {}
        }

        SkillContent(
            skills = skillData,
            registerNow = {
                registerLoading = true
                if (it != null) viewModel.registerUser(it)
            },
            dataFromRegister = dataFromRegister
        )
    }

    val registerResponse by viewModel.response.collectAsState(initial = UiState.Loading)
    LaunchedEffect(registerResponse) {
        registerResponse.let { response ->
            when (response) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    registerLoading = false
                    Toast.makeText(context, response.data.message, Toast.LENGTH_SHORT).show()
                    backToLogin()
                }
                is UiState.Error -> {
                    registerLoading = false
                    Toast.makeText(context, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun SkillContent(
    skills: List<Skill>,
    registerNow: (UserJobSeeker?) -> Unit,
    dataFromRegister: UserJobSeeker?,
    isLoading: Boolean = false
) {
    var skillOneSelected by rememberSaveable { mutableIntStateOf(-1) }
    var skillTwoSelected by rememberSaveable { mutableIntStateOf(-1) }

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Blue40, Blue80)
                    )
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            if (skills.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box {
                        CircularProgressIndicator(
                            color = Light,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            } else {
                Box {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 128.dp, top = 100.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalItemSpacing = 8.dp,
                    ) {
                        items(skills, key = { it.id }) {
                            TextButton(
                                modifier = Modifier
                                    .background(
                                        color = Light.copy(
                                            alpha = if (skillOneSelected == it.id || skillTwoSelected == it.id)
                                                .8f else .1f
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 8.dp),
                                onClick = {
                                    if (skillOneSelected == it.id) {
                                        skillOneSelected = -1
                                    } else if (skillTwoSelected == it.id) {
                                        skillTwoSelected = -1
                                    } else {
                                        if (skillOneSelected == -1) {
                                            skillOneSelected = it.id
                                        } else if (skillTwoSelected == -1){
                                            skillTwoSelected = it.id
                                        }
                                    }
                                }
                            ) {
                                Text(
                                    text = it.name.toString(),
                                    color = if (skillOneSelected == it.id || skillTwoSelected == it.id)
                                        Blue80 else Light
                                )
                            }
                        }
                    }
                }
            }
            Text(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Blue40, Color.Transparent)
                        )
                    )
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 38.dp, bottom = 16.dp),
                text = "Choose Your Skills",
                fontSize = 28.sp,
                color = Light,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }

        AnimatedVisibility(visible = true) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Blue80)
                        )
                    )
                    .height(148.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(64.dp),
                    onClick = {
                        registerNow(
                            dataFromRegister?.copy(skill_one = skillOneSelected, skill_two = skillTwoSelected)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Light,
                        contentColor = Blue40
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp).padding(end = 8.dp),
                            color = Blue80
                        )
                    }
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = stringResource(id = R.string.continue_register),
                        fontSize = 20.sp,
                        color = Blue80
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SkillPreview() {
    JourneyTheme {
        SkillContent(
            skills = listOf(),
            dataFromRegister = UserJobSeeker(),
            registerNow = {}
        )
    }
}