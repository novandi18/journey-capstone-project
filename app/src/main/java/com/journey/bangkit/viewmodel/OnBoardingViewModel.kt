package com.journey.bangkit.viewmodel

import androidx.lifecycle.ViewModel
import com.journey.bangkit.data.local.auth.AuthEntity
import com.journey.bangkit.repository.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val repository: OnBoardingRepository
) : ViewModel() {
    suspend fun upsertAll() {
        repository.upsertAll()
    }

    suspend fun getAll(): AuthEntity = repository.getAll()

    suspend fun setOnBoardingIsCompleted(isCompleted: Boolean) {
        repository.updateOnBoarding(isCompleted)
    }
}