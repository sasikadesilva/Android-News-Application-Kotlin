package com.example.newsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application : Application() { // Remove the extra { from here

    override fun onCreate() {
        super.onCreate()
    }
}