package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.journey.bangkit.data.model.UserApplyStatusResponse
import com.journey.bangkit.data.model.Vacancy
import com.journey.bangkit.data.model.VacancyResponse
import com.journey.bangkit.repository.HomeJobProviderRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeJobProviderViewModel @Inject constructor(
    private val repository: HomeJobProviderRepository
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