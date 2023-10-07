package com.journey.bangkit.data.local.vacancy

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface VacancyDao {
    @Upsert
    suspend fun upsertAll(vacancies: List<VacancyEntity>)

    @Query("SELECT * FROM vacancyentity")
    fun pagingSource(): PagingSource<Int, VacancyEntity>

    @Query("DELETE FROM vacancyentity")
    suspend fun clearAll()
}