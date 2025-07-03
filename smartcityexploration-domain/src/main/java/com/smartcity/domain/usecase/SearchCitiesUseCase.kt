package com.smartcity.domain.usecase

import com.smartcity.domain.model.City
import com.smartcity.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.smartcity.domain.util.Result

class SearchCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    operator fun invoke(query: String): Flow<Result<List<City>>> {
        return repository.searchCities(query)
    }
}