package com.byandev.fundametalandroid17

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start_service.setOnClickListener(this)
        btn_start_intent_service.setOnClickListener(this)
        btn_start_bound_service.setOnClickListener(this)
        btn_stop_bound_service.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start_service -> {
                // Service dijalankan dengan cara berikut ini:
                val mStartServiceIntent = Intent(this@MainActivity, MyService::class.java) // menjalankan pada clas myService pada metode onStartCommand()
                startService(mStartServiceIntent) // note : karena kita akan menjalan kan service bukan pindah halaman
            }
            R.id.btn_start_intent_service -> {

            }
            R.id.btn_start_bound_service -> {

            }
            R.id.btn_stop_bound_service -> {

            }
        }
    }
}
