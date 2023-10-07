package com.journey.bangkit.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.local.vacancy.VacancyEntity
import com.journey.bangkit.data.mappers.toVacancyEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class JourneyRemoteMediator(
    private val db: JourneyDatabase,
    private val api: JourneyApi
) : RemoteMediator<Int, VacancyEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, VacancyEntity>
    ): MediatorResult {
        return try {
            val remoteKey = db.remoteKeyDao.getRemoteKey()
            val currentPage = db.pageDao.getPage()

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else remoteKey.page + 1
                }
            }

            if (currentPage.pageType != 4) {
                val vacancies = when (currentPage.pageType) {
                    1 -> api.getVacancies(page = loadKey)
                    2 -> api.getPopularVacancies(page = loadKey)
                    3 -> api.getLatestVacancies(page = loadKey)
                    else -> api.getVacancies(page = loadKey)
                }

                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.remoteKeyDao.resetPage()
                        db.vacancyDao.clearAll()
                    }
                    db.remoteKeyDao.increasePage()
                    val vacancyEntities = vacancies.vacancies.map { it.toVacancyEntity() }
                    db.vacancyDao.upsertAll(vacancyEntities)
                }

                MediatorResult.Success(
                    endOfPaginationReached = vacancies.vacancies.isEmpty()
                )
            } else {
                MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}