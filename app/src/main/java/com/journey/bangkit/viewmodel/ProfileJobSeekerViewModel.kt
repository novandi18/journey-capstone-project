package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.ProfileUser
import com.journey.bangkit.repository.ProfileJobSeekerRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileJobSeekerViewModel @Inject constructor(
    private val repository: ProfileJobSeekerRepository
) : ViewModel() {
    private val _user: MutableStateFlow<UiState<ProfileUser>> = MutableStateFlow(UiState.Loading)
    val user: StateFlow<UiState<ProfileUser>> get() = _user

    fun getUser() {
        viewModelScope.launch {
            repository.getProfile()
                .catch {
                    _user.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _user.value = UiState.Success(result)
                }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            repository.logoutProfile()
        }
    }
}