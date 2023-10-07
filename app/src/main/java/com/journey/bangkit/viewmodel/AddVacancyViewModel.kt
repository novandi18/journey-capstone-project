package com.journey.bangkit.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.AddVacancy
import com.journey.bangkit.data.model.AddVacancyResponse
import com.journey.bangkit.data.model.City
import com.journey.bangkit.data.model.Province
import com.journey.bangkit.data.model.Sector
import com.journey.bangkit.data.model.Skill
import com.journey.bangkit.repository.AddVacancyRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddVacancyViewModel @Inject constructor(
    private val repository: AddVacancyRepository
) : ViewModel() {
    private val _response: MutableStateFlow<UiState<AddVacancyResponse>> = MutableStateFlow(
        UiState.Loading)
    val response: StateFlow<UiState<AddVacancyResponse>> get() = _response

    private val _skills: MutableStateFlow<UiState<List<Skill>>> = MutableStateFlow(UiState.Loading)
    val skills: StateFlow<UiState<List<Skill>>> get() = _skills

    fun addVacancy(data: AddVacancy) {
        viewModelScope.launch {
            repository.doAddVacancy(data)
                .catch {
                    _response.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _response.value = UiState.Success(result)
                }
        }
    }

    fun getSkills(context: Context) {
        viewModelScope.launch {
            repository.getSkillsData(context)
                .catch {
                    _skills.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _skills.value = UiState.Success(result)
                }
        }
    }
}