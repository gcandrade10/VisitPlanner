package com.example.visitplanner.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.visitplanner.Place

@Entity(tableName = "place")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var label: String = "",
    var isVisited: Boolean = false,
    var description: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)

fun Place.toEntity() = PlaceEntity(
    label = label,
    isVisited = isVisited,
    description = description,
    latitude = latLong.first,
    longitude = latLong.second
)

fun PlaceEntity.toPlace() = Place(
    id = id,
    label = label,
    isVisited = isVisited,
    description = description,
    latLong = Pair(latitude, longitude)
)

