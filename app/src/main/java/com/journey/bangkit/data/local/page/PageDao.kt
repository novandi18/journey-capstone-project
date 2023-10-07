package com.journey.bangkit.data.local.page

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setInitialPage(page: PageEntity)

    @Query("UPDATE pageentity SET pageType = :page WHERE id = 1")
    suspend fun switchPage(page: Int)

    @Query("SELECT * FROM pageentity")
    suspend fun getPage(): PageEntity
}