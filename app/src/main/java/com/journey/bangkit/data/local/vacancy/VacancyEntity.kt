package com.journey.bangkit.data.local.vacancy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val placement_address: String,
    val description: String,
    val created_at: String,
    val updated_at: String,
    val deadline_time: String,
    val skill_one_name: String,
    val skill_two_name: String,
    val disability_name: String,
    val company_logo: String,
    val job_type: Int,
    val sector_name: String,
    val company_name: String
)