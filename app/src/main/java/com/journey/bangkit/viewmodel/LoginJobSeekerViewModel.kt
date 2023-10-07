package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.LoginJobSeekerResponse
import com.journey.bangkit.repository.LoginJobSeekerRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginJobSeekerViewModel @Inject constructor(
    private val repository: LoginJobSeekerRepository
) : ViewModel() {
    private val _response: MutableStateFlow<UiState<LoginJobSeekerResponse>> = MutableStateFlow(UiState.Loading)
    val response: StateFlow<UiState<LoginJobSeekerResponse>> get() = _response

    fun loginUser(email: String, password: String) {
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