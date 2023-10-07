package com.journey.bangkit.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.api.JourneyMLApi
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.local.search.SearchEntity
import com.journey.bangkit.data.local.vacancy.VacancyEntity
import com.journey.bangkit.data.mappers.toVacancy
import com.journey.bangkit.data.model.AllVacancy
import com.journey.bangkit.data.model.MachineLearning
import com.journey.bangkit.data.model.Vacancy
import com.journey.bangkit.data.paging.SearchPagingSource
import com.journey.bangkit.data.source.DisabilityDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeJobSeekerRepository @Inject constructor(
    private val pager: Pager<Int, VacancyEntity>,
    private val db: JourneyDatabase,
    private val apiMl: JourneyMLApi,
    private val api: JourneyApi
) {
    fun getAllVacancies() = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toVacancy() }
        }

    fun getVacancySearch(position: String): Pager<Int, Vacancy> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            )
        ) {
            SearchPagingSource(
                api = api,
                query = position
            )
        }
    }

    suspend fun getRecommendedData(): Flow<Pair<List<String>, List<AllVacancy>>> {
        val user = db.loginDao.getLogin()
        val userData = api.getUser(token = user.token, id = user.user_id)
        val request = apiMl.getPredict(
            MachineLearning(
                skill_one = userData.user.skill_one_name.toString(),
                skill_two = userData.user.skill_two_name.toString(),
                id_disability = DisabilityDataSource.disabilities.filter { it.name == userData.user.disability_name }[0].id
            )
        )
        val allVacancies = api.getAllVacancyWithoutPager()
        return flowOf(Pair(request.predictions, allVacancies.vacancies))
    }

    suspend fun switchPage(page: Int) {
        db.pageDao.switchPage(page)
    }

    suspend fun getSearchHistory(): List<SearchEntity> =
        db.searchDao.getSearchHistory()

    suspend fun insertSearchHistory(keyword: String) {
        db.searchDao.insertSearchHistory(
            SearchEntity(keyword = keyword)
        )
    }

    suspend fun deleteSearchHistory(search: SearchEntity) {
        db.searchDao.deleteSearchHistory(search)
    }
}