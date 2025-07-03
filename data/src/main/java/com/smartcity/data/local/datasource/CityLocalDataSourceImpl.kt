package com.smartcity.data.local.datasource

import com.smartcity.data.local.dao.CityDao
import com.smartcity.data.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

class CityLocalDataSourceImpl(
    private val dao: CityDao
) : CityLocalDataSource {

    override fun searchCities(query: String): Flow<List<CityEntity>> {
        return dao.searchCities(query)
    }

    override suspend fun insertCities(cities: List<CityEntity>) {
        dao.insertCities(cities)
    }
}