package com.journey.bangkit.data.api

import com.journey.bangkit.data.model.MachineLearning
import com.journey.bangkit.data.model.MachineLearningResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface JourneyMLApi {
    @POST("predict")
    suspend fun getPredict(
        @Body request: MachineLearning
    ) : MachineLearningResponse

    companion object {
        const val BASE_URL_ML = "https://ml-pchfpsfuwq-et.a.run.app/"
    }
}