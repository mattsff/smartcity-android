package com.smartcity.data.remote.datasource

import com.smartcity.data.remote.CityApiService
import com.smartcity.data.remote.model.CityRemoteDto
import com.smartcity.domain.util.Result
import com.smartcity.domain.util.AppException
import retrofit2.HttpException
import java.io.IOException

class CityRemoteDataSourceImpl(
    private val api: CityApiService,
) : CityRemoteDataSource {

    override suspend fun fetchCities(): Result<List<CityRemoteDto>> {
        return try {
            val response = api.fetchCities()
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(AppException.NetworkError)
        } catch (e: HttpException) {
            Result.Error(AppException.ApiError(e.code(), e.message()))
        } catch (e: Exception) {
            Result.Error(AppException.UnknownError)
        }
    }
}