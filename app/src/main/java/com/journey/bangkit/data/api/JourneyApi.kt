package com.journey.bangkit.data.api

import com.journey.bangkit.data.model.AddVacancy
import com.journey.bangkit.data.model.AddVacancyResponse
import com.journey.bangkit.data.model.AllVacancyResponse
import com.journey.bangkit.data.model.Applicants
import com.journey.bangkit.data.model.ApplicantsResponse
import com.journey.bangkit.data.model.LoginJobProviderResponse
import com.journey.bangkit.data.model.LoginJobSeekerResponse
import com.journey.bangkit.data.model.LoginRequest
import com.journey.bangkit.data.model.ProfileCompanyResponse
import com.journey.bangkit.data.model.ProfileUserResponse
import com.journey.bangkit.data.model.UserApplyResponse
import com.journey.bangkit.data.model.UserApplyStatusResponse
import com.journey.bangkit.data.model.UserJobProvider
import com.journey.bangkit.data.model.UserJobSeeker
import com.journey.bangkit.data.model.UserRegisterResponse
import com.journey.bangkit.data.model.VacancyDetail
import com.journey.bangkit.data.model.VacancyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface JourneyApi {
    @GET("vacancies")
    suspend fun getVacancies(
        @Query("key") key: String? = null,
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10
    ) : VacancyResponse

    @GET("vacancies/latest")
    suspend fun getLatestVacancies(
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10
    ) : VacancyResponse

    @GET("vacancies/popular")
    suspend fun getPopularVacancies(
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10
    ) : VacancyResponse

    @GET("vacancies/{id}")
    suspend fun getVacancy(
        @Path("id") id: String
    ) : VacancyDetail

    @POST("users/login")
    suspend fun loginJobSeeker(
        @Body loginRequest: LoginRequest
    ) : LoginJobSeekerResponse

    @POST("companies/login")
    suspend fun loginJobProvider(
        @Body loginRequest: LoginRequest
    ) : LoginJobProviderResponse

    @POST("users")
    suspend fun registerJobSeeker(
        @Body request: UserJobSeeker
    ) : UserRegisterResponse

    @POST("companies")
    suspend fun registerJobProvider(
        @Body request: UserJobProvider
    ) : UserRegisterResponse

    @GET("users/{id}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : ProfileUserResponse

    @GET("companies/{id}")
    suspend fun getCompany(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : ProfileCompanyResponse

    @POST("users/{userId}/vacancies/{vacancyId}/apply")
    suspend fun postJobApply(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Path("vacancyId") vacancyId: String
    ) : UserApplyResponse

    @GET("users/applicants/{userId}")
    suspend fun getApplyStatus(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ) : UserApplyStatusResponse

    @GET("companies/{companyId}/vacancies")
    suspend fun getCompanyVacancies(
        @Header("Authorization") token: String,
        @Path("companyId") companyId: String,
    ) : VacancyResponse

    @POST("companies/{companyId}/vacancies")
    suspend fun addVacancy(
        @Header("Authorization") token: String,
        @Path("companyId") companyId: String,
        @Body vacancyRequest: AddVacancy
    ) : AddVacancyResponse

    @GET("vacancies/all-vacancies")
    suspend fun getAllVacancyWithoutPager() : AllVacancyResponse

    @GET("companies/{companyId}/vacancies/{vacancyId}/applicants")
    suspend fun getApplicants(
        @Header("Authorization") token: String,
        @Path("companyId") companyId: String,
        @Path("vacancyId") vacancyId: String
    ) : List<Applicants>

    @PUT("companies/{companyId}/vacancies/{vacancyId}/applicants/{applicantsId}/accept")
    suspend fun postAcceptApplicants(
        @Header("Authorization") token: String,
        @Path("companyId") companyId: String,
        @Path("vacancyId") vacancyId: String,
        @Path("applicantsId") applicantsId: String
    ) : ApplicantsResponse

    @PUT("companies/{companyId}/vacancies/{vacancyId}/applicants/{applicantsId}/reject")
    suspend fun postRejectApplicants(
        @Header("Authorization") token: String,
        @Path("companyId") companyId: String,
        @Path("vacancyId") vacancyId: String,
        @Path("applicantsId") applicantsId: String
    ) : ApplicantsResponse

    @GET("vacancies/name/{position}")
    suspend fun getSearchVacancy(
        @Path("position") position: String,
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10
    ) : VacancyResponse

    companion object {
        const val BASE_URL = "https://journey-pchfpsfuwq-et.a.run.app/api/"
    }
}