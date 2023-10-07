package com.journey.bangkit.data.local.remotekey

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setInitialRemoteKey(page: RemoteKeyEntity)

    @Query("SELECT * FROM remotekeyentity")
    suspend fun getRemoteKey(): RemoteKeyEntity

    @Query("UPDATE remotekeyentity SET page = page + 1 WHERE id = 1")
    suspend fun increasePage()

    @Query("UPDATE remotekeyentity SET page = 1 WHERE id = 1")
    suspend fun resetPage()
}