package com.byandev.submission2uiux.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.byandev.submission2uiux.BuildConfig
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SaveDataTheme
import com.byandev.submission2uiux.utils.NotificationUtils
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*


class SettingsActivity : AppCompatActivity() {

    private var switchTheme: Switch? = null
    private var swNotificationAlarm: Switch? = null
    private lateinit var saveDataTheme: SaveDataTheme

    private val mNotificationTime = Calendar.getInstance().timeInMillis + 300000 // set 5 minute
    private var mNotified = false


    override fun onCreate(savedInstanceState: Bundle?) {
        saveDataTheme = SaveDataTheme(this)
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        listener()

        if (saveDataTheme.loadModeState() == true) {
            switchTheme?.isChecked = true

        }

        if (saveDataTheme.saveStateAlarm() == true) {
            swNotificationAlarm?.isChecked = true
        }


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
                saveDataTheme.setDarkModeState(true)
                resetApp()
            } else {
                saveDataTheme.setDarkModeState(false)
                resetApp()
            }
        }
        swNotificationAlarm?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!mNotified) {
                    NotificationUtils().setNotification(mNotificationTime, this)
                    Toast.makeText(this, "true mNotified", Toast.LENGTH_SHORT).show()
                    saveDataTheme.setSw(true)
                }
            } else {
                NotificationUtils().resetNotification(mNotificationTime, this)
                Toast.makeText(this, "false mNotified", Toast.LENGTH_SHORT).show()
                saveDataTheme.setSw(false)
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
