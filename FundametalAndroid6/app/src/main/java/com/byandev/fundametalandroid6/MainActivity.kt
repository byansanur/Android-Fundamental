package com.byandev.fundametalandroid6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // mengganti nilai dari judul halaman pada actionbar di dalam MainActivity.
        supportActionBar?.title = "Google Pixel"
    }
}
