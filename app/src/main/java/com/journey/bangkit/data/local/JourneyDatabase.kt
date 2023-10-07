package com.journey.bangkit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.journey.bangkit.data.local.auth.AuthDao
import com.journey.bangkit.data.local.auth.AuthEntity
import com.journey.bangkit.data.local.login.LoginDao
import com.journey.bangkit.data.local.login.LoginEntity
import com.journey.bangkit.data.local.page.PageDao
import com.journey.bangkit.data.local.page.PageEntity
import com.journey.bangkit.data.local.remotekey.RemoteKeyDao
import com.journey.bangkit.data.local.remotekey.RemoteKeyEntity
import com.journey.bangkit.data.local.search.SearchDao
import com.journey.bangkit.data.local.search.SearchEntity
import com.journey.bangkit.data.local.vacancy.VacancyDao
import com.journey.bangkit.data.local.vacancy.VacancyEntity

@Database(
    entities = [VacancyEntity::class, AuthEntity::class,
        LoginEntity::class, PageEntity::class, RemoteKeyEntity::class, SearchEntity::class],
    version = 6
)
abstract class JourneyDatabase: RoomDatabase() {
    abstract val vacancyDao: VacancyDao
    abstract val authDao: AuthDao
    abstract val loginDao: LoginDao
    abstract val pageDao: PageDao
    abstract val remoteKeyDao: RemoteKeyDao
    abstract val searchDao: SearchDao
}