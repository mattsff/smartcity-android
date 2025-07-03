package com.smartcity.data.local.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.smartcity.data.local.dao.CityDao
import com.smartcity.data.local.entity.CityEntity
import com.smartcity.data.local.entity.CityFtsEntity

@Database(
    entities = [CityEntity::class, CityFtsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}