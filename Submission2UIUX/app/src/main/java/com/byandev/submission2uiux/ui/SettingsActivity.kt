package com.byandev.submission2uiux.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.byandev.submission2uiux.BuildConfig
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SaveDataTheme
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private var switch: Switch? = null
    private lateinit var saveDataTheme: SaveDataTheme

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        saveDataTheme = SaveDataTheme(this)
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        switch = findViewById<View>(R.id.swTheme) as Switch?
        if (saveDataTheme.loadModeState() == true) {
            switch!!.isChecked = true
        }

        switch!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveDataTheme.setDarkModeState(true)
                resetApp()
            } else {
                saveDataTheme.setDarkModeState(false)
                resetApp()
            }
        }

        card1.setOnClickListener {
            val i = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(i)
        }

        tvVersion.text = resources.getString(R.string.version) +" "+ BuildConfig.VERSION_NAME

    }

    private fun resetApp() {
        val i = Intent(applicationContext, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        finish()
    }

}
