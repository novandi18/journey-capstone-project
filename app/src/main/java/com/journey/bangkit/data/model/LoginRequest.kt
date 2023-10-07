package com.journey.bangkit.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginJobSeekerResponse(
    val status: String = "",
    val id_user: String = "",
    val role_id: Int = 1,
    val token: String = "",
    val errorMessage: String? = null
)

data class LoginJobProviderResponse(
    val status: String = "",
    val id_company: String = "",
    val role_id: Int = 1,
    val token: String = "",
    val errorMessage: String? = null
)