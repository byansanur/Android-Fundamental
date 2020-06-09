package com.byandev.fundametalandroid26.settingPreferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.byandev.fundametalandroid26.R

/*
    NOTE !!!
    Project ini berbeda package mohon perhatikan manifest file untuk memulai aplikasi ini.

    pada folder ini untuk menjaga dimana ketika aplikasi di tutup data tidak ikut hilang
 */
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, MyPreferenceFragment()).commit()
    }
}