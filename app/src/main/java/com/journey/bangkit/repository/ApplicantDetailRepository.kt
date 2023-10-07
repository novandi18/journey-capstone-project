package com.journey.bangkit.repository

import com.google.gson.Gson
import com.journey.bangkit.data.api.ApiException
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.model.ApiErrorResponse
import com.journey.bangkit.data.model.Applicants
import com.journey.bangkit.data.model.ApplicantsResponse
import com.journey.bangkit.data.model.LoginJobSeekerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ApplicantDetailRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
) {
    suspend fun getApplicants(vacancyId: String): Flow<List<Applicants>> {
        val user = db.loginDao.getLogin()
        val request = api.getApplicants(
            token = user.token,
            companyId = user.user_id,
            vacancyId = vacancyId
        )
        return flowOf(request)
    }

    suspend fun doAccepted(applicantId: String, vacancyId: String): Flow<ApplicantsResponse> {
        return try {
            val user = db.loginDao.getLogin()
            val request = api.postAcceptApplicants(
                applicantsId = applicantId,
                token = user.token,
                companyId = user.user_id,
                vacancyId = vacancyId
            )
            flowOf(request)
        } catch (e: ApiException) {
            val errorResponse = Gson().fromJson(e.message.toString(), ApiErrorResponse::class.java)
            flowOf(ApplicantsResponse(message = errorResponse.message, status = "Error"))
        }
    }

    suspend fun doRejected(applicantId: String, vacancyId: String): Flow<ApplicantsResponse> {
        return try {
            val user = db.loginDao.getLogin()
            val request = api.postRejectApplicants(
                applicantsId = applicantId,
                token = user.token,
                companyId = user.user_id,
                vacancyId = vacancyId
            )
            flowOf(request)
        } catch (e: ApiException) {
            val errorResponse = Gson().fromJson(e.message.toString(), ApiErrorResponse::class.java)
            flowOf(ApplicantsResponse(message = errorResponse.message, status = "Error"))
        }
    }
}