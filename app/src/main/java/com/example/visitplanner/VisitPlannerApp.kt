package com.example.visitplanner

import android.app.Application
import androidx.room.Room
import com.example.visitplanner.add_place.AddPlaceViewModel
import com.example.visitplanner.detail.PlaceDetailViewModel
import com.example.visitplanner.persistence.AppDatabase
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.MapsInitializer
import com.google.android.libraries.places.api.Places
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import java.util.*

class VisitPlannerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            MapsInitializer.initialize(applicationContext)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_maps_key), Locale.US)
        }
        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@VisitPlannerApp)
            modules(repositoryModule)
        }
    }
}

val repositoryModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, "database-name"
        ).build().placeDao()
    }
    viewModel { AddPlaceViewModel(get()) }
    viewModel { PlacesListViewModel(get()) }
    viewModel { PlaceDetailViewModel(get()) }
    viewModel { MainViewModel(get()) }
}
