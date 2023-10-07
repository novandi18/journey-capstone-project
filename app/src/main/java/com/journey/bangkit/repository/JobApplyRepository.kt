package com.journey.bangkit.repository

import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.model.UserApplyStatusResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class JobApplyRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
) {
    suspend fun getJobApplies(): Flow<UserApplyStatusResponse> {
        val user = db.loginDao.getLogin()
        val request = api.getApplyStatus(token = user.token, userId = user.user_id)
        return flowOf(request)
    }
}