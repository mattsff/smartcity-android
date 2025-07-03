package com.smartcity.domain.usecase
import com.smartcity.domain.repository.CityRepository
import javax.inject.Inject
import com.smartcity.domain.util.Result

class SyncCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.syncCities()
    }
}