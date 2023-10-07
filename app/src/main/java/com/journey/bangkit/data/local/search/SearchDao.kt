package com.journey.bangkit.data.local.search

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDao {
    @Query("SELECT * FROM search")
    suspend fun getSearchHistory(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchHistory(search: SearchEntity)

    @Delete
    suspend fun deleteSearchHistory(search: SearchEntity)
}