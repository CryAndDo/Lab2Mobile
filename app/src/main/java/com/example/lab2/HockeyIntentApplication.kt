package com.example.lab2

import android.app.Application

class HockeyIntentApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        HockeyRepository.initialize(this)
    }
}