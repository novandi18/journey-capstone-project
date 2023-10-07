package com.journey.bangkit.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.Disability
import com.journey.bangkit.data.model.Skill
import com.journey.bangkit.data.model.UserJobProvider
import com.journey.bangkit.data.model.UserJobSeeker
import com.journey.bangkit.data.model.UserRegisterResponse
import com.journey.bangkit.repository.RegisterJobSeekerRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterJobSeekerViewModel @Inject constructor(
    private val repository: RegisterJobSeekerRepository
) : ViewModel() {
    private val _disability: MutableStateFlow<UiState<Pair<List<Disability>, List<Skill>>>> = MutableStateFlow(UiState.Loading)
    val disability: StateFlow<UiState<Pair<List<Disability>, List<Skill>>>> get() = _disability

    private val _response: MutableStateFlow<UiState<UserRegisterResponse>> = MutableStateFlow(
        UiState.Loading)
    val response: StateFlow<UiState<UserRegisterResponse>> get() = _response

    fun getDisabilitySkillData(context: Context) {
        viewModelScope.launch {
            repository.getDisabilitySkill(context)
                .catch {
                    _disability.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _disability.value = UiState.Success(result)
                }
        }
    }

    fun registerUser(request: UserJobSeeker) {
        viewModelScope.launch {
            repository.doRegister(request)
                .catch {
                    _response.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _response.value = UiState.Success(result)
                }
        }
    }
}