package com.sedatkaganaktas.pipeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PipeAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}