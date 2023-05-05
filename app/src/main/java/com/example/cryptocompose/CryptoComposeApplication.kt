package com.example.cryptocompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CryptoComposeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}