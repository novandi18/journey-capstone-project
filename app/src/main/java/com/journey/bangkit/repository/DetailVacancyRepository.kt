package com.journey.bangkit.repository

import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.model.UserApplyResponse
import com.journey.bangkit.data.model.UserApplyStatusResponse
import com.journey.bangkit.data.model.VacancyDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DetailVacancyRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
) {
    suspend fun getVacancyById(id: String): Flow<Pair<VacancyDetail, UserApplyStatusResponse>> {
        val user = db.loginDao.getLogin()
        val vacancy = api.getVacancy(id)
        val vacancyStatus = api.getApplyStatus(
            token = user.token,
            userId = user.user_id
        )
        return flowOf(Pair(vacancy, vacancyStatus))
    }

    suspend fun applyVacancy(vacancyId: String): Flow<UserApplyResponse> {
        val user = db.loginDao.getLogin()
        val request = api.postJobApply(
            token = user.token,
            vacancyId = vacancyId,
            userId = user.user_id
        )
        return flowOf(request)
    }
}