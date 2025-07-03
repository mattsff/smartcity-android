package com.smartcity.data.repository

import com.smartcity.data.local.datasource.CityLocalDataSource
import com.smartcity.data.mapper.toDomain
import com.smartcity.data.mapper.toEntity
import com.smartcity.data.remote.datasource.CityRemoteDataSource
import com.smartcity.domain.model.City
import com.smartcity.domain.repository.CityRepository
import com.smartcity.domain.util.AppException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch
import com.smartcity.domain.util.Result

class CityRepositoryImpl(
    private val localDataSource: CityLocalDataSource,
    private val remoteDataSource: CityRemoteDataSource,
) : CityRepository {

    override suspend fun syncCities(): Result<Unit> {
        return when (val remoteResult = remoteDataSource.fetchCities()) {
            is Result.Success -> {
                try {
                    val entities = remoteResult.data.map { it.toEntity() }
                    localDataSource.insertCities(entities)
                    Result.Success(Unit)
                } catch (e: Exception) {
                    Result.Error(AppException.UnknownError)
                }
            }

            is Result.Error -> {
                Result.Error(remoteResult.exception)
            }
        }
    }

    override fun searchCities(query: String): Flow<Result<List<City>>> {
        return localDataSource.searchCities(query)
            .map { list -> Result.Success(list.map { it.toDomain() }) }
            .catch { e ->
                throw AppException.UnknownError
            }
    }
}
