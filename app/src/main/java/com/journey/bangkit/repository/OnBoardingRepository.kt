package com.journey.bangkit.repository

import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.local.auth.AuthEntity
import com.journey.bangkit.data.local.page.PageEntity
import com.journey.bangkit.data.local.remotekey.RemoteKeyEntity
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(
    private val db: JourneyDatabase
) {
    suspend fun upsertAll() {
        db.remoteKeyDao.setInitialRemoteKey(RemoteKeyEntity())
        db.authDao.upsertAllAuth(AuthEntity())
        db.pageDao.setInitialPage(PageEntity())
        db.remoteKeyDao.resetPage()
    }

    suspend fun getAll(): AuthEntity = db.authDao.getOnBoardingAndAuth()

    suspend fun updateOnBoarding(isCompleted: Boolean) {
        db.authDao.setIsOnBoardingCompleted(isCompleted)
    }
}