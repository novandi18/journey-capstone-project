package com.journey.bangkit.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            val errorResponse = response.body?.string()
            throw ApiException(errorResponse, response)
        }

        return response
    }
}

class ApiException(message: String?, val response: Response) : IOException(message)