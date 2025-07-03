package com.smartcity.data.remote

import retrofit2.http.GET
import com.smartcity.data.remote.model.CityRemoteDto

interface CityApiService {

    @GET("hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/cities.json")
    suspend fun fetchCities(): List<CityRemoteDto>
}