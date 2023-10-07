package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.LoginJobProviderResponse
import com.journey.bangkit.repository.LoginJobProviderRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginJobProviderViewModel @Inject constructor(
    private val repository: LoginJobProviderRepository
) : ViewModel() {
    private val _response: MutableStateFlow<UiState<LoginJobProviderResponse>> = MutableStateFlow(
        UiState.Loading)
    val response: StateFlow<UiState<LoginJobProviderResponse>> get() = _response

    fun loginCompany(email: String, password: String) {
        viewModelScope.launch {
            repository.doLogin(email, password)
                .catch {
                    _response.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _response.value = UiState.Success(result)
                }
        }
    }
}