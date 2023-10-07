package com.journey.bangkit.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.model.Vacancy

class SearchPagingSource(
    private val api: JourneyApi,
    val query: String
) : PagingSource<Int, Vacancy>() {
    companion object {
        const val PAGE_SIZE = 10
        private const val INITIAL_LOAD_SIZE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        return try {
            val position = params.key ?: INITIAL_LOAD_SIZE

            val result = api.getSearchVacancy(position = query, page = position)
            val nextKey = if (result.vacancies.isEmpty()) {
                null
            } else {
                position + (params.loadSize / PAGE_SIZE)
            }
            return LoadResult.Page(
                data = result.vacancies,
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}