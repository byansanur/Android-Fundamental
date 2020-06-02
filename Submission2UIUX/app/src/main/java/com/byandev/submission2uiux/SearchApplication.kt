package com.byandev.submission2uiux

import android.app.Application
import com.byandev.submission2uiux.data.SaveDataTheme

class SearchApplication : Application() {

    lateinit var saveDataTheme: SaveDataTheme

    override fun onCreate() {
        super.onCreate()
        saveDataTheme = SaveDataTheme(this)
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}