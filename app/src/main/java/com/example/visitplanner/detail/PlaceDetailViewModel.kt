package com.example.visitplanner.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visitplanner.persistence.PlaceDao
import kotlinx.coroutines.launch

class PlaceDetailViewModel(private val placeDao: PlaceDao) : ViewModel() {

    fun markAsVisited(id: Int, isVisited: Boolean) {
        viewModelScope.launch {
            placeDao.markAsVisited(id, isVisited)
        }
    }

}