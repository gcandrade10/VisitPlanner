package com.example.visitplanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.libraries.places.widget.Autocomplete
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE)
        val isDummyDataLoaded = prefs.getBoolean(DUMMY_DATA_LOADED_KEY, false)
        if (!isDummyDataLoaded) {
            mainViewModel.loadDummy(resources.openRawResource(R.raw.dummy))
            val editor = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE).edit()
            editor.putBoolean(DUMMY_DATA_LOADED_KEY, true)
            editor.apply()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(data)
                val myPlace = Place(
                    id = -1,
                    label = place.name ?: "",
                    latLong = Pair(place.latLng?.latitude ?: 0.0, place.latLng?.longitude ?: 0.0),
                    description = "",
                    isVisited = false
                )
                val direction =
                    PlacesListFragmentDirections.actionPlacesListFragmentToAddPlaceFragment(
                        myPlace
                    )
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(direction)
            }
        }
    }

    companion object {
        const val AUTOCOMPLETE_REQUEST_CODE = 1
        const val SHARED_PREFERENCE = "SHARED PREFERENCE"
        const val DUMMY_DATA_LOADED_KEY = "DUMMY_DATA_LOADED"
    }
}