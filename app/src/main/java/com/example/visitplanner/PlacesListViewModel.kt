package com.example.visitplanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visitplanner.persistence.PlaceDao
import com.example.visitplanner.persistence.toPlace
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PlacesListViewModel(private val placeDao: PlaceDao) : ViewModel() {

    val placesLiveData = MutableLiveData<List<Place>>()

    init {
        viewModelScope.launch {
            placeDao.getPlaces().map {
                it.map { place ->
                    place.toPlace()
                }
            }.collect {
                placesLiveData.postValue(it)
            }
        }
    }
}
