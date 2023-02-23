package com.example.testcompose.util

import android.app.Application
import com.example.testcompose.repositories.NoteRepository

class BaseApp:Application() {
    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}