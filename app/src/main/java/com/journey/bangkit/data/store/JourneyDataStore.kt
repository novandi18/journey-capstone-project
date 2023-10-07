package com.journey.bangkit.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JourneyDataStore(private val context: Context) {
    val isOnBoardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ON_BOARDING_KEY] ?: false
        }

    val isLoginAsJobSeeker: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGIN_JOBSEEKER] ?: false
        }

    val isLoginAsJobProvider: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGIN_JOBPROVIDER] ?: false
        }

    suspend fun saveOnBoardingState(isCompleted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ON_BOARDING_KEY] = isCompleted
        }
    }

    suspend fun setLoginAsJobSeeker() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGIN_JOBPROVIDER] = false
            preferences[IS_LOGIN_JOBSEEKER] = true
        }
    }

    suspend fun setLoginAsJobProvider() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGIN_JOBSEEKER] = false
            preferences[IS_LOGIN_JOBPROVIDER] = true
        }
    }

    suspend fun setLogoutAsJobSeeker() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGIN_JOBSEEKER] = false
        }
    }

    suspend fun setLogoutAsJobProvider() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGIN_JOBSEEKER] = false
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("JourneyDataStore")
        val ON_BOARDING_KEY = booleanPreferencesKey("on_boarding")
        val IS_LOGIN_JOBSEEKER = booleanPreferencesKey("login_jobseeker")
        val IS_LOGIN_JOBPROVIDER = booleanPreferencesKey("login_jobprovider")
    }
}
