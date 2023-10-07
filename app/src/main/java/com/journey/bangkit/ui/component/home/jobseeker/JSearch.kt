package com.journey.bangkit.ui.component.home.jobseeker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.journey.bangkit.R
import com.journey.bangkit.data.model.Vacancy
import com.journey.bangkit.data.source.JourneyDataSource.jobTypes
import com.journey.bangkit.ui.component.JSearchCard
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Dark
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.utils.toDate
import com.journey.bangkit.viewmodel.HomeJobSeekerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JSearch(
    modifier: Modifier = Modifier,
    setActive: (Boolean) -> Unit,
    active: Boolean,
    viewModel: HomeJobSeekerViewModel,
    navigateToDetail: (String) -> Unit
) {
    var isSearch by rememberSaveable { mutableStateOf(false) }
    var searchIdDeleted by remember { mutableIntStateOf(0) }
    val search by viewModel.search.collectAsStateWithLifecycle()
    val searchHistory = viewModel.searchHistory.observeAsState()
    val result: LazyPagingItems<Vacancy> = viewModel.searchResult.collectAsLazyPagingItems()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(searchQuery) {
        viewModel.getSearchHistory()
    }

    SearchBar(
        modifier = modifier
            .background(color = if (active) Color.White else Color.Transparent)
            .padding(
                bottom = if (active) 0.dp else 8.dp,
                start = if (active) 0.dp else 8.dp,
                end = if (active) 0.dp else 8.dp
            )
            .fillMaxWidth(),
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = {
            if (searchQuery.isNotEmpty()) {
                isSearch = true
                viewModel.setSearch(searchQuery)
                viewModel.insertSearchHistory(searchQuery)
            }
        },
        active = active,
        onActiveChange = { setActive(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search),
                tint = Blue40
            )
        },
        trailingIcon = {
            if (active && searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    setActive(search.isNotEmpty())
                    isSearch = false
                    searchQuery = ""
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.cancel)
                    )
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.White,
            dividerColor = Dark.copy(alpha = .3f)
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_placeholder),
                color = Dark.copy(alpha = .5f)
            )
        }
    ) {
        if (isSearch) {
            if (result.loadState.refresh == LoadState.Loading) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize()
                ) {
                    items(count = result.itemCount) { vacancy ->
                        JSearchCard(
                            title = result[vacancy]!!.placement_address,
                            imageUrl = result[vacancy]!!.company_logo,
                            jobType = jobTypes[result[vacancy]!!.job_type - 1],
                            disabilityType = result[vacancy]!!.disability_name,
                            skillOne = result[vacancy]!!.skill_one_name,
                            skillTwo = result[vacancy]!!.skill_two_name,
                            deadline = result[vacancy]!!.deadline_time.toDate(),
                            description = result[vacancy]!!.description,
                            setClick = navigateToDetail,
                            id = result[vacancy]!!.id
                        )
                    }
                }
            }
        } else {
            LazyColumn {
                items(count = searchHistory.value!!.size) {
                    Card(
                        modifier = modifier.fillMaxWidth(),
                        onClick = {
                            searchIdDeleted = searchHistory.value!![it].id
                            searchQuery = searchHistory.value!![it].keyword
                            viewModel.setSearch(searchQuery)
                            isSearch = true
                        },
                        shape = RoundedCornerShape(0.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Dark
                        )
                    ) {
                        Row(
                            modifier = modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(imageVector = Icons.Filled.History, contentDescription = "", tint = Dark)
                                Text(text = searchHistory.value!![it].keyword, color = Dark)
                            }
                            IconButton(onClick = {
                                viewModel.deleteSearchHistory(searchHistory.value!![it])
                            }) {
                                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "", tint = Dark)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JSearchPreview() {
    JourneyTheme {
        JSearch(
            setActive = {},
            active = false,
            viewModel = hiltViewModel(),
            navigateToDetail = {}
        )
    }
}