package com.smartcity.data.mapper

import com.smartcity.data.local.entity.CityEntity
import com.smartcity.data.remote.model.CityRemoteDto
import com.smartcity.domain.model.City

fun CityRemoteDto.toEntity(): CityEntity {
    return CityEntity(
        id = id,
        name = name,
        country = country,
        lat = coord.lat,
        lon = coord.lon
    )
}

fun CityEntity.toDomain(): City {
    return City(
        id = id,
        name = name,
        country = country,
        lat = lat,
        lon = lon,
        isFavorite = isFavorite
    )
}