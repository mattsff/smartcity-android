package com.smartcity.domain.repository

import com.smartcity.domain.model.City
import kotlinx.coroutines.flow.Flow
import com.smartcity.domain.util.Result

interface CityRepository {
    suspend fun syncCities(): Result<Unit>
    fun searchCities(query: String): Flow<Result<List<City>>>
    suspend fun hasCitiesStored(): Boolean
}

