package com.byandev.submission2uiux.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.byandev.submission2uiux.BuildConfig
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SharedPref
import com.byandev.submission2uiux.notification.AlarmNotificationReceiver
import com.byandev.submission2uiux.notification.NotificationScheduler
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    private var switchTheme: Switch? = null
    private var swNotificationAlarm: Switch? = null
    private lateinit var sharedPref: SharedPref


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = SharedPref(this)
        if (sharedPref.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        listener()

        if (sharedPref.loadModeState() == true) {
            switchTheme?.isChecked = true

        }

        sharedPref.getReminderStatus()?.let { swNotificationAlarm?.setChecked(it) }



        setSwitch()

    }

    @SuppressLint("SetTextI18n")
    private fun listener() {

        switchTheme = findViewById<View>(R.id.swTheme) as Switch?
        swNotificationAlarm = findViewById<View>(R.id.swNotificationAlarm) as Switch?

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        card1.setOnClickListener {
            val i = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(i)
        }

        tvVersion.text = resources.getString(R.string.version) +" "+ BuildConfig.VERSION_NAME

    }

    override fun onResume() {
        super.onResume()
        setSwitch()
    }


    private fun setSwitch() {
        switchTheme?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPref.setDarkModeState(true)
                resetApp()
            } else {
                sharedPref.setDarkModeState(false)
                resetApp()
            }
        }
        swNotificationAlarm?.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.setRemainder(isChecked)
            if (isChecked) {
                NotificationScheduler.setReminder(this, AlarmNotificationReceiver::class.java)
                Log.d("Setting alarm", "setSwitch: on")
            } else {
                NotificationScheduler.cancelReminder(this, AlarmNotificationReceiver::class.java)
                Log.d("Setting alarm", "setSwitch: off")
            }
        }
    }


    private fun resetApp() {
        val i = Intent(applicationContext, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        finish()
    }

}
