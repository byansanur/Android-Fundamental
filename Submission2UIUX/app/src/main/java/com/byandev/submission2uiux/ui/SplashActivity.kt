package com.byandev.submission2uiux.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SaveDataTheme

class SplashActivity : AppCompatActivity() {

    private lateinit var saveDataTheme: SaveDataTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        saveDataTheme = SaveDataTheme(this)
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}