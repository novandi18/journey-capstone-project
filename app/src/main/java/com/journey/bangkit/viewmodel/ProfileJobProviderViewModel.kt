package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.ProfileCompany
import com.journey.bangkit.repository.ProfileJobProviderRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileJobProviderViewModel @Inject constructor(
    private val repository: ProfileJobProviderRepository
) : ViewModel() {
    private val _company: MutableStateFlow<UiState<ProfileCompany>> = MutableStateFlow(UiState.Loading)
    val company: StateFlow<UiState<ProfileCompany>> get() = _company

    fun getCompany() {
        viewModelScope.launch {
            repository.getProfile()
                .catch {
                    _company.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _company.value = UiState.Success(result)
                }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            repository.logoutProfile()
        }
    }
}