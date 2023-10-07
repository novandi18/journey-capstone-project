package com.journey.bangkit.data.local.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthEntity(
    @PrimaryKey
    val id: Int = 1,
    val isOnBoardingCompleted: Boolean = false,
    val isLoginAsJobSeeker: Boolean = false,
    val isLoginAsJobProvider: Boolean = false
)