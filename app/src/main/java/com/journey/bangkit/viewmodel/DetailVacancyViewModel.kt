package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.UserApplyResponse
import com.journey.bangkit.data.model.UserApplyStatusResponse
import com.journey.bangkit.data.model.VacancyDetail
import com.journey.bangkit.repository.DetailVacancyRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailVacancyViewModel @Inject
constructor(private val repository: DetailVacancyRepository) : ViewModel() {
    private val _vacancy: MutableStateFlow<UiState<Pair<VacancyDetail, UserApplyStatusResponse>>> = MutableStateFlow(UiState.Loading)
    val vacancy: StateFlow<UiState<Pair<VacancyDetail, UserApplyStatusResponse>>> get() = _vacancy

    private val _response: MutableStateFlow<UiState<UserApplyResponse>> = MutableStateFlow(UiState.Loading)
    val response: StateFlow<UiState<UserApplyResponse>> get() = _response

    fun getVacancy(id: String) {
        viewModelScope.launch {
            repository.getVacancyById(id)
                .catch {
                    _vacancy.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _vacancy.value = UiState.Success(result)
                }
        }
    }

    fun doApplyVacancy(vacancyId: String) {
        viewModelScope.launch {
            repository.applyVacancy(vacancyId)
                .catch {
                    _response.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _response.value = UiState.Success(result)
                }
        }
    }
}