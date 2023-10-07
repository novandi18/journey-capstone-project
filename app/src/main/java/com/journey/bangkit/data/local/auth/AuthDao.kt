package com.journey.bangkit.data.local.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsertAllAuth(onBoardingAuth: AuthEntity)

    @Query("SELECT * FROM authentity")
    suspend fun getOnBoardingAndAuth(): AuthEntity

    @Query("UPDATE authentity SET isOnBoardingCompleted = :isCompleted WHERE id = 1")
    suspend fun setIsOnBoardingCompleted(isCompleted: Boolean)

    @Query("UPDATE authentity SET isLoginAsJobSeeker = :isLoggedIn WHERE id = 1")
    suspend fun setIsLoginAsJobSeeker(isLoggedIn: Boolean)

    @Query("UPDATE authentity SET isLoginAsJobProvider = :isLoggedIn WHERE id = 1")
    suspend fun setIsLoginAsJobProvider(isLoggedIn: Boolean)
}