package com.smartcity.domain.usecase
import com.smartcity.domain.repository.CityRepository
import javax.inject.Inject
import com.smartcity.domain.util.Result

class LoadCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        val hasCities = repository.hasCitiesStored()

        return if (!hasCities) {
            repository.syncCities()
        } else {
            Result.Success(Unit)
        }
    }
}

