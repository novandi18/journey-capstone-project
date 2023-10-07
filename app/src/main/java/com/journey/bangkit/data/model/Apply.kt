package com.journey.bangkit.data.model

data class UserApplyStatusResponse(
    val status: String = "",
    val page: Int = 0,
    val limit: Int = 0,
    val totalJobApply: Int = 0,
    val totalPages: Int = 0,
    val data: List<UserApplyStatus>
)

data class UserApplyResponse(
    val message: String = ""
)

data class UserApplyStatus(
    val id: String = "",
    val status: String = "",
    val applied_at: String = "",
    val company_name: String = "",
    val vacancy_placement_address: String = "",
    val disability_name: String = "",
    val skill_one_name: String = "",
    val skill_two_name: String = ""
)