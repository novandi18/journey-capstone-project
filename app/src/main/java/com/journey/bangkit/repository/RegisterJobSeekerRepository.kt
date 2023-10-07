package com.journey.bangkit.repository

import android.content.Context
import com.google.gson.Gson
import com.journey.bangkit.data.api.ApiException
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.model.ApiErrorResponse
import com.journey.bangkit.data.model.Disability
import com.journey.bangkit.data.model.LoginJobSeekerResponse
import com.journey.bangkit.data.model.Skill
import com.journey.bangkit.data.model.UserJobSeeker
import com.journey.bangkit.data.model.UserRegisterResponse
import com.journey.bangkit.data.source.DisabilityDataSource
import com.journey.bangkit.utils.getSkills
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegisterJobSeekerRepository @Inject constructor(
    private val api: JourneyApi
) {
    fun getDisabilitySkill(context: Context): Flow<Pair<List<Disability>, List<Skill>>> {
        val disabilities = DisabilityDataSource.disabilities
        val skills = getSkills(context)
        return flowOf(Pair(disabilities, skills))
    }

    suspend fun doRegister(data: UserJobSeeker): Flow<UserRegisterResponse> {
        return try {
            flowOf(api.registerJobSeeker(data))
        } catch (e: ApiException) {
            val errorResponse = Gson().fromJson(e.message, ApiErrorResponse::class.java)
            flowOf(UserRegisterResponse(message = errorResponse.message))
        }
    }
}