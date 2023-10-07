package com.journey.bangkit.data.local.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity(
    @PrimaryKey
    val user_id: String,
    val token: String,
    val role_id: Int
)