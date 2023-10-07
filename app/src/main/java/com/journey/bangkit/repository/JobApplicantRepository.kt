package com.journey.bangkit.repository

import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.model.Applicants
import com.journey.bangkit.data.model.ApplicantsResponse
import com.journey.bangkit.data.model.VacancyResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class JobApplicantRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
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