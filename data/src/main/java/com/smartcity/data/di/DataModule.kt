package com.smartcity.data.di

import android.content.Context
import androidx.room.Room
import com.smartcity.data.BuildConfig
import com.smartcity.data.local.dao.CityDao
import com.smartcity.data.local.datasource.CityLocalDataSource
import com.smartcity.data.local.datasource.CityLocalDataSourceImpl
import com.smartcity.data.local.db.CityDatabase
import com.smartcity.data.remote.CityApiService
import com.smartcity.data.remote.datasource.CityRemoteDataSource
import com.smartcity.data.remote.datasource.CityRemoteDataSourceImpl
import com.smartcity.data.repository.CityRepositoryImpl
import com.smartcity.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideCityApiService(retrofit: Retrofit): CityApiService =
        retrofit.create(CityApiService::class.java)

    @Provides
    @Singleton
    fun provideCityDatabase(@ApplicationContext context: Context): CityDatabase =
        Room.databaseBuilder(context, CityDatabase::class.java, "city_database")
            .build()

    @Provides
    fun provideCityDao(db: CityDatabase): CityDao = db.cityDao()

    @Provides
    @Singleton
    fun provideCityRemoteDataSource(api: CityApiService): CityRemoteDataSource =
        CityRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideCityLocalDataSource(dao: CityDao): CityLocalDataSource =
        CityLocalDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideCityRepository(
        localDataSource: CityLocalDataSource,
        remoteDataSource: CityRemoteDataSource,
    ): CityRepository = CityRepositoryImpl(localDataSource, remoteDataSource)
}
