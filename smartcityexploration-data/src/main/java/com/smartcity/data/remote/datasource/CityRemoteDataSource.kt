package com.smartcity.data.remote.datasource

import com.smartcity.data.remote.model.CityRemoteDto
import com.smartcity.domain.util.Result

interface CityRemoteDataSource {
    suspend fun fetchCities(): Result<List<CityRemoteDto>>
}
