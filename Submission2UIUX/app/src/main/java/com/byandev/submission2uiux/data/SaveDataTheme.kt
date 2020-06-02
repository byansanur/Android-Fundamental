package com.byandev.submission2uiux.data

import android.content.Context
import android.content.SharedPreferences

class SaveDataTheme(context: Context?) {
//    private val sharedPreferencesVal: SharedPreferences = context.getSharePreference("Action", Context.MODE_PRIVATE)
    private var sharedPreferencesVar:  SharedPreferences = context!!.getSharedPreferences("file", Context.MODE_PRIVATE)


    fun setDarkModeState(state: Boolean?) {
        val editor = sharedPreferencesVar.edit()
        editor.putBoolean("Dark", state!!)
        editor.apply()
    }

    fun loadModeState(): Boolean? {
        val state = sharedPreferencesVar.getBoolean("Dark", false)
        return state
    }

}