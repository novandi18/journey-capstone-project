package com.journey.bangkit.repository

import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.model.VacancyResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class HomeJobProviderRepository @Inject constructor(
    private val db: JourneyDatabase,
    private val api: JourneyApi
) {
    suspend fun getAllVacancies(): Flow<VacancyResponse> {
        val request = db.loginDao.getLogin()
        val response = api.getCompanyVacancies(
            token = request.token,
            companyId = request.user_id
        )
        return flowOf(response)
    }
}