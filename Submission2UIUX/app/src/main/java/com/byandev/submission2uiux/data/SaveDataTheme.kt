package com.byandev.submission2uiux.data

import android.content.Context
import android.content.SharedPreferences

class SaveDataTheme(context: Context?) {
    private var sharedPreferencesVar: SharedPreferences? = context?.getSharedPreferences("file", Context.MODE_PRIVATE)


    fun setDarkModeState(state: Boolean?) {
        val editor = sharedPreferencesVar?.edit()
        editor?.putBoolean("Dark", state!!)
        editor?.apply()
    }

    fun loadModeState(): Boolean? {
        return sharedPreferencesVar?.getBoolean("Dark", false)
    }

    fun setSw(state: Boolean?) {
        val edior = sharedPreferencesVar?.edit()
        edior?.putBoolean("alarm", state!!)
        edior?.apply()
    }

    fun saveStateAlarm(): Boolean? {
        return sharedPreferencesVar?.getBoolean("alarm", false)
    }



}