package com.journey.bangkit.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.bangkit.data.model.City
import com.journey.bangkit.data.model.Province
import com.journey.bangkit.data.model.Sector
import com.journey.bangkit.data.model.UserJobProvider
import com.journey.bangkit.data.model.UserRegisterResponse
import com.journey.bangkit.repository.RegisterJobProviderRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterJobProviderViewModel @Inject constructor(
    private val repository: RegisterJobProviderRepository
) : ViewModel() {
    private val _region: MutableStateFlow<UiState<Triple<List<Province>, List<City>, List<Sector>>>> = MutableStateFlow(UiState.Loading)
    val region: StateFlow<UiState<Triple<List<Province>, List<City>, List<Sector>>>> get() = _region

    private val _response: MutableStateFlow<UiState<UserRegisterResponse>> = MutableStateFlow(
        UiState.Loading)
    val response: StateFlow<UiState<UserRegisterResponse>> get() = _response

    fun getRegion(context: Context, provinceId: Int = 11) {
        viewModelScope.launch {
            repository.getProvinceCitySector(context, provinceId)
                .catch {
                    _region.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _region.value = UiState.Success(result)
                }
        }
    }

    fun registerCompany(request: UserJobProvider) {
        viewModelScope.launch {
            repository.doRegister(request)
                .catch {
                    _response.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _response.value = UiState.Success(result)
                }
        }
    }
}