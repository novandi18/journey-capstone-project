package com.journey.bangkit.data.model

data class ProfileUserResponse(
     val status: String,
     val user: ProfileUser
)

data class ProfileCompanyResponse(
    val status: String,
    val company: ProfileCompany
)

data class ProfileUser(
    val id: String? = null,
    val full_name: String? = null,
    val email: String? = null,
    val skill_one_name: String? = null,
    val skill_two_name: String? = null,
    val disability_name: String? = null,
    val address: String? = null,
    val gender: String? = null,
    val age: String? = null,
    val phone_number: String? = null,
    val profile_photo_url: String? = null
)

data class ProfileCompany(
    val id: String? = null,
    val name: String? = null,
    val address: String? = null,
    val city: String? = null,
    val province: String? = null,
    val logo: String? = null,
    val employees: Int? = null,
    val email: String? = null,
    val roleId: Int? = null,
    val sector_name: String? = null
)