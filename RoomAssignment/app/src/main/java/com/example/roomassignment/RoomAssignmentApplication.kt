package com.example.roomassignment

import android.app.Application
import timber.log.Timber

class RoomAssignmentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}