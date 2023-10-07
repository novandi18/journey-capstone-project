package com.journey.bangkit.data.model

data class MachineLearning(
    val skill_one: String,
    val skill_two: String,
    val id_disability: Int
)

data class MachineLearningResponse(
    val predictions: List<String>
)