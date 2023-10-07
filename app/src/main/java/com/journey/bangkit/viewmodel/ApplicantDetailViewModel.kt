package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.Applicants
import com.journey.bangkit.data.model.ApplicantsResponse
import com.journey.bangkit.repository.ApplicantDetailRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantDetailViewModel @Inject constructor(
    private val repository: ApplicantDetailRepository
) : ViewModel() {
    private val _response: MutableStateFlow<UiState<List<Applicants>>> = MutableStateFlow(
        UiState.Loading)
    val response: StateFlow<UiState<List<Applicants>>> get() = _response

    private val _result: MutableStateFlow<UiState<ApplicantsResponse>> = MutableStateFlow(
        UiState.Loading)
    val result: StateFlow<UiState<ApplicantsResponse>> get() = _result

    fun getCompanyApplicant(vacancyId: String) {
        viewModelScope.launch {
            repository.getApplicants(vacancyId)
                .catch {
                    _response.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _response.value = UiState.Success(result)
                }
        }
    }

    fun letAccepted(applicantId: String, vacancyId: String) {
        viewModelScope.launch {
            repository.doAccepted(applicantId, vacancyId)
                .catch {
                    _result.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _result.value = UiState.Success(result)
                }
        }
    }

    fun letRejected(applicantId: String, vacancyId: String) {
        viewModelScope.launch {
            repository.doRejected(applicantId, vacancyId)
                .catch {
                    _result.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _result.value = UiState.Success(result)
                }
        }
    }
}