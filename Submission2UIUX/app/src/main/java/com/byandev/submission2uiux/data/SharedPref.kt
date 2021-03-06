package com.byandev.submission2uiux.data

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context?) {
    private var sharedPreferencesVar: SharedPreferences? =
        context?.getSharedPreferences("file", Context.MODE_PRIVATE)


    fun setDarkModeState(state: Boolean?) {
        val editor = sharedPreferencesVar?.edit()
        editor?.putBoolean("Dark", state!!)
        editor?.apply()
    }

    fun loadModeState(): Boolean? {
        return sharedPreferencesVar?.getBoolean("Dark", false)
    }

    // Settings Page Set Reminder
    companion object {
        private const val REMAINDER_STATUS = "remainder_status"
    }

    fun setRemainder(state: Boolean) {
        val editor = sharedPreferencesVar?.edit()
        editor?.putBoolean(REMAINDER_STATUS, state)
        editor?.apply()
    }

    fun getReminderStatus(): Boolean? {
        return sharedPreferencesVar?.getBoolean(REMAINDER_STATUS, false)
    }



}