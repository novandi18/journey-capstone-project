package com.journey.bangkit.data.local.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val keyword: String
)