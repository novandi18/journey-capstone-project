package com.journey.bangkit.repository

import android.content.Context
import com.google.gson.Gson
import com.journey.bangkit.data.api.ApiException
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.model.ApiErrorResponse
import com.journey.bangkit.data.model.City
import com.journey.bangkit.data.model.LoginJobProviderResponse
import com.journey.bangkit.data.model.Province
import com.journey.bangkit.data.model.Sector
import com.journey.bangkit.data.model.UserJobProvider
import com.journey.bangkit.data.model.UserJobSeeker
import com.journey.bangkit.data.model.UserRegisterResponse
import com.journey.bangkit.utils.getCityById
import com.journey.bangkit.utils.getProvince
import com.journey.bangkit.utils.getSector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegisterJobProviderRepository @Inject constructor(
    private val api: JourneyApi
) {
    fun getProvinceCitySector(context: Context, provinceId: Int)
    : Flow<Triple<List<Province>,List<City>, List<Sector>>> {
        val province = getProvince(context)
        val city = getCityById(context = context, id = provinceId)
        val sector = getSector(context)
        return flowOf(Triple(province, city, sector))
    }

    suspend fun doRegister(data: UserJobProvider): Flow<UserRegisterResponse> {
        return try {
            flowOf(
                api.registerJobProvider(data)
            )
        } catch (e: ApiException) {
            val errorResponse = Gson().fromJson(e.message, ApiErrorResponse::class.java)
            flowOf(UserRegisterResponse(message = errorResponse.message))
        }
    }
}