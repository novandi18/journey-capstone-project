package com.journey.bangkit.data.local.login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveLogin(loginData: LoginEntity)

    @Query("SELECT * FROM loginentity")
    suspend fun getLogin(): LoginEntity

    @Query("DELETE FROM loginentity")
    suspend fun clearLogin()
}