package com.example.visitplanner.add_place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visitplanner.Place
import com.example.visitplanner.persistence.PlaceDao
import com.example.visitplanner.persistence.toEntity
import kotlinx.coroutines.launch

class AddPlaceViewModel(private val placeDao: PlaceDao) : ViewModel() {

    val completeLiveData = MutableLiveData<Unit>()

    fun save(place: Place) {
        viewModelScope.launch {
            val placeEntity = place.toEntity()
            placeDao.insertPlace(placeEntity)
            completeLiveData.postValue(Unit)
        }
    }


}