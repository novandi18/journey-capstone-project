package com.journey.bangkit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.journey.bangkit.data.local.search.SearchEntity
import com.journey.bangkit.data.model.AllVacancy
import com.journey.bangkit.data.model.Vacancy
import com.journey.bangkit.repository.HomeJobSeekerRepository
import com.journey.bangkit.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeJobSeekerViewModel @Inject
constructor(private val repository: HomeJobSeekerRepository) : ViewModel() {
    val vacancies: Flow<PagingData<Vacancy>> = repository.getAllVacancies().cachedIn(viewModelScope)

    private val _predict: MutableStateFlow<UiState<Pair<List<String>, List<AllVacancy>>>> = MutableStateFlow(
        UiState.Loading)
    val predict: StateFlow<UiState<Pair<List<String>, List<AllVacancy>>>> get() = _predict

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow().stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    private val _searchHistory = MutableLiveData<List<SearchEntity>>()
    val searchHistory: LiveData<List<SearchEntity>> get() = _searchHistory

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult = search.debounce(300).flatMapLatest { query ->
        repository.getVacancySearch(query).flow.cachedIn(viewModelScope)
    }

    fun setSearch(position: String) {
        _search.value = position
    }

    suspend fun setVacancyCategory(page: Int) {
        repository.switchPage(page)
    }

    fun getPredict() {
        viewModelScope.launch {
            repository.getRecommendedData()
                .catch {
                    _predict.value = UiState.Error(it.message.toString())
                }
                .collect { result ->
                    _predict.value = UiState.Success(result)
                }
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            _searchHistory.value = repository.getSearchHistory()
        }
    }

    fun insertSearchHistory(keyword: String) {
        viewModelScope.launch {
            repository.insertSearchHistory(keyword)
        }
    }

    fun deleteSearchHistory(search: SearchEntity) {
        viewModelScope.launch {
            repository.deleteSearchHistory(search)
        }
    }
}