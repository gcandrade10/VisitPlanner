package com.example.visitplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visitplanner.persistence.PlaceDao
import com.example.visitplanner.persistence.PlaceEntity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream


class MainViewModel(private val placeDao: PlaceDao) : ViewModel() {

    private fun inputStreamToString(inputStream: InputStream): String? {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            null
        }
    }

    fun loadDummy(inputStream: InputStream) {
        viewModelScope.launch {
            val myJson = inputStreamToString(inputStream)
            val list: List<PlaceModel> =
                Gson().fromJson(myJson, object : TypeToken<List<PlaceModel?>?>() {}.type)
            list.map {
                placeDao.insertPlace(it.toEntity())
            }
        }
    }
}


data class PlaceModel(
    val label: String,
    val visited: Boolean,
    val description: String,
    @SerializedName("lat_lng")
    val latLong: List<Double>
)

fun PlaceModel.toEntity() = PlaceEntity(
    label = label,
    isVisited = visited,
    description = description,
    latitude = latLong[0],
    longitude = latLong[1]
)