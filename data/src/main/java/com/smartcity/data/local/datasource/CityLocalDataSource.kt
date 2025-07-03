package com.smartcity.data.local.datasource

import com.smartcity.data.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

interface CityLocalDataSource {
    fun searchCities(query: String): Flow<List<CityEntity>>
    suspend fun insertCities(cities: List<CityEntity>)
}
