package com.example.visitplanner.persistence

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Insert
    suspend fun insertPlace(placeEntity: PlaceEntity)

    @Query("Select * from place")
    fun getPlaces(): Flow<List<PlaceEntity>>

    @Query("UPDATE place SET isVisited = :isVisited WHERE id = :id")
    suspend fun markAsVisited(id: Int, isVisited: Boolean)
}

@Database(entities = [PlaceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}