package com.journey.bangkit.repository

import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.model.ProfileUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProfileJobSeekerRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
) {
    suspend fun getProfile(): Flow<ProfileUser> {
        val user = db.loginDao.getLogin()
        val response = api.getUser(
            token = user.token, id = user.user_id
        )
        return flowOf(response.user)
    }

    suspend fun logoutProfile() {
        db.loginDao.clearLogin()
        db.authDao.setIsLoginAsJobSeeker(isLoggedIn = false)
    }
}