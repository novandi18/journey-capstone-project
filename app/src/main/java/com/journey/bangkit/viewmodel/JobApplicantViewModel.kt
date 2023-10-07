package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.Applicants
import com.journey.bangkit.data.model.ApplicantsResponse
import com.journey.bangkit.data.model.VacancyResponse
import com.journey.bangkit.repository.HomeJobProviderRepository
import com.journey.bangkit.repository.JobApplicantRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobApplicantViewModel @Inject constructor(
    private val repository: JobApplicantRepository
) : ViewModel() {
    private val _response: MutableStateFlow<UiState<VacancyResponse>> = MutableStateFlow(
        UiState.Loading)
    val response: StateFlow<UiState<VacancyResponse>> get() = _response

    fun getJobCompany() {
        viewModelScope.launch {
            repository.getAllVacancies()
                .catch {
                    _response.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _response.value = UiState.Success(result)
                }
        }
    }
}