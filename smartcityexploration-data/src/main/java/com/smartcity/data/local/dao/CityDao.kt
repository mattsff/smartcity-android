package com.smartcity.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smartcity.data.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)

    @Query("""
        SELECT cities.* FROM cities
        JOIN city_fts ON cities.id = city_fts.docid
        WHERE city_fts MATCH :query || '*'
        ORDER BY name ASC, country ASC
        LIMIT 50
    """)
    fun searchCities(query: String): Flow<List<CityEntity>>

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun getCount(): Int
}

