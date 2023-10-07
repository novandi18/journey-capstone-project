package com.journey.bangkit.repository

import com.google.gson.Gson
import com.journey.bangkit.data.api.ApiException
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.local.login.LoginEntity
import com.journey.bangkit.data.model.ApiErrorResponse
import com.journey.bangkit.data.model.LoginJobProviderResponse
import com.journey.bangkit.data.model.LoginRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LoginJobProviderRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
) {
    suspend fun doLogin(email: String, password: String): Flow<LoginJobProviderResponse> {
        return try {
            val request = api.loginJobProvider(
                LoginRequest(email, password)
            )
            db.loginDao.saveLogin(
                LoginEntity(user_id = request.id_company, role_id = request.role_id, token = request.token)
            )
            db.authDao.setIsLoginAsJobProvider(isLoggedIn = true)

            flowOf(request)
        } catch (e: ApiException) {
            val errorResponse = Gson().fromJson(e.message, ApiErrorResponse::class.java)
            flowOf(LoginJobProviderResponse(errorMessage = errorResponse.message))
        }
    }
}