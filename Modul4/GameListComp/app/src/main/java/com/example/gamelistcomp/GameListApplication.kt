package com.example.gamelistcomp

import android.app.Application
import timber.log.Timber

class GameListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}