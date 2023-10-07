package com.journey.bangkit.repository

import com.google.gson.Gson
import com.journey.bangkit.data.api.ApiException
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.local.login.LoginEntity
import com.journey.bangkit.data.model.ApiErrorResponse
import com.journey.bangkit.data.model.LoginJobSeekerResponse
import com.journey.bangkit.data.model.LoginRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LoginJobSeekerRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
) {
    suspend fun doLogin(email: String, password: String): Flow<LoginJobSeekerResponse> {
        return try {
            val request = api.loginJobSeeker(
                LoginRequest(email, password)
            )
            db.loginDao.saveLogin(
                LoginEntity(user_id = request.id_user, token = request.token, role_id = request.role_id)
            )
            db.authDao.setIsLoginAsJobSeeker(isLoggedIn = true)

            flowOf(request)
        } catch (e: ApiException) {
            val errorResponse = Gson().fromJson(e.message, ApiErrorResponse::class.java)
            flowOf(LoginJobSeekerResponse(errorMessage = errorResponse.message))
        }
    }
}