package com.journey.bangkit.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User(
    val id: String = "",
    val full_name: String = "",
    val email: String = "",
    val address: String = "",
    val profile_photo_url: String = "",
    val gender: String = "",
    val age: String = "",
    val phone_number: String = "",
    val disability_name: String = "",
    val skill_one_name: String = "",
    val skill_two_name: String = ""
)

@Parcelize
data class UserJobSeeker(
    val full_name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val skill_one: Int? = null,
    val skill_two: Int? = null,
    val id_disability: Int? = null,
    val address: String? = null,
    val gender: String? = null,
    val age: Int? = null,
    val phone_number: String? = null
) : Parcelable

data class UserJobProvider(
    val name: String,
    val address: String,
    val city: String,
    val province: String,
    val employees: Int,
    val email: String,
    val password: String,
    val id_sector: Int
)

data class UserRegisterResponse(
    val message: String,
    val status: String? = null,
    val id: String? = null,
)

data class ApiErrorResponse(
    val status: String = "",
    val message: String = ""
)