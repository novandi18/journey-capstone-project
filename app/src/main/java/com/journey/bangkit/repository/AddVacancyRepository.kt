package com.journey.bangkit.repository

import android.content.Context
import com.google.gson.Gson
import com.journey.bangkit.data.api.ApiException
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.model.AddVacancy
import com.journey.bangkit.data.model.AddVacancyResponse
import com.journey.bangkit.data.model.ApiErrorResponse
import com.journey.bangkit.data.model.City
import com.journey.bangkit.data.model.LoginJobSeekerResponse
import com.journey.bangkit.data.model.Province
import com.journey.bangkit.data.model.Sector
import com.journey.bangkit.data.model.Skill
import com.journey.bangkit.utils.getCityById
import com.journey.bangkit.utils.getProvince
import com.journey.bangkit.utils.getSector
import com.journey.bangkit.utils.getSkills
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AddVacancyRepository @Inject constructor(
    private val api: JourneyApi,
    private val db: JourneyDatabase
) {
    suspend fun doAddVacancy(data: AddVacancy): Flow<AddVacancyResponse> {
        return try {
            val user = db.loginDao.getLogin()
            val request = api.addVacancy(
                companyId = user.user_id,
                vacancyRequest = data,
                token = user.token
            )
            flowOf(AddVacancyResponse(message = request.message))
        } catch (e: ApiException) {
            val errorResponse = Gson().fromJson(e.message, ApiErrorResponse::class.java)
            flowOf(AddVacancyResponse(message = errorResponse.message))
        }
    }

    fun getSkillsData(context: Context): Flow<List<Skill>> {
        val skills = getSkills(context)
        return flowOf(skills)
    }
}