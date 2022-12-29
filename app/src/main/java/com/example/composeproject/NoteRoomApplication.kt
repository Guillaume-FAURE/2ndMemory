package com.example.composeproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteRoomApplication: Application() {
    override fun onCreate(){
        super.onCreate()

    }
}