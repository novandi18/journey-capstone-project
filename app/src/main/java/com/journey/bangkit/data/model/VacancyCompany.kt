package com.journey.bangkit.data.model


data class AddVacancy(
    val placement_address: String,
    val description: String,
    val id_disability: Int,
    val skill_one: String,
    val skill_two: String,
    val job_type: Int,
    val deadline_time: String,
)

data class AddVacancyResponse(
    val message: String
)