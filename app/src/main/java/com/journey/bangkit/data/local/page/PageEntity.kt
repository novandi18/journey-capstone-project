package com.journey.bangkit.data.local.page

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageEntity(
    @PrimaryKey
    val id: Int = 1,
    val pageType: Int = 1
)