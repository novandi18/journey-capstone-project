package com.journey.bangkit.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.journey.bangkit.data.local.JourneyDatabase
import com.journey.bangkit.data.local.vacancy.VacancyEntity
import com.journey.bangkit.data.api.JourneyApi
import com.journey.bangkit.data.api.JourneyMLApi
import com.journey.bangkit.data.api.LoggingInterceptor
import com.journey.bangkit.data.remote.JourneyRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor = LoggingInterceptor()

    @Provides
    @Singleton
    fun provideJourneyDatabase(@ApplicationContext context: Context): JourneyDatabase {
        return Room.databaseBuilder(
            context,
            JourneyDatabase::class.java,
            "vacancies.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideJourneyApi(): JourneyApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(JourneyApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideJourneyMLApi(): JourneyMLApi {
        return Retrofit.Builder()
            .baseUrl(JourneyMLApi.BASE_URL_ML)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideJourneyPager(db: JourneyDatabase, api: JourneyApi)
    : Pager<Int, VacancyEntity> = Pager(
        config = PagingConfig(
            pageSize = 10
        ),
        remoteMediator = JourneyRemoteMediator(
            db = db,
            api = api
        ),
        pagingSourceFactory = {
            db.vacancyDao.pagingSource()
        }
    )
}