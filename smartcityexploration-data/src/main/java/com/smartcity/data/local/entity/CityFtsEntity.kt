package com.smartcity.data.local.entity
import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = CityEntity::class)
@Entity(tableName = "city_fts")
data class CityFtsEntity(
    val name: String,
    val country: String
)