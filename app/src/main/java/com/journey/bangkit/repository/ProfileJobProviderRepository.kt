package com.journey.bangkit.repository

import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.model.ProfileCompany
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProfileJobProviderRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
) {
    suspend fun getProfile(): Flow<ProfileCompany> {
        val company = db.loginDao.getLogin()
        val response = api.getCompany(
            token = company.token, id = company.user_id
        )
        return flowOf(response.company)
    }

    suspend fun logoutProfile() {
        db.loginDao.clearLogin()
        db.authDao.setIsLoginAsJobProvider(isLoggedIn = false)
    }
}