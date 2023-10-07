package com.journey.bangkit.data.model

data class Applicants(
    val id: String,
    val full_name: String,
    val email: String,
    val address: String,
    val profile_photo_url: String,
    val gender: String,
    val age: String,
    val phone_number: String,
    val applied_at: String,
    val disability_name: String,
    val skill_one_name: String,
    val skill_two_name: String
)

data class ApplicantsResponse(
    val status: String,
    val message: String
)