package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.UserApplyStatusResponse
import com.journey.bangkit.repository.JobApplyRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobApplyViewModel @Inject constructor(
    private val repository: JobApplyRepository
) : ViewModel() {
    private val _response: MutableStateFlow<UiState<UserApplyStatusResponse>> = MutableStateFlow(UiState.Loading)
    val response: StateFlow<UiState<UserApplyStatusResponse>> get() = _response

    fun getJobApply() {
        viewModelScope.launch {
            repository.getJobApplies()
                .catch {
                    _response.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _response.value = UiState.Success(result)
                }
        }
    }
}