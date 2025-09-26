package com.example.newsapp

import android.app.Application


class Application : Application() { // Remove the extra { from here
    override fun onCreate() {
        super.onCreate()
    }
}