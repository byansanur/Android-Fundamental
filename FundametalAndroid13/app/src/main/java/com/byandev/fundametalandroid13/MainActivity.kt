package com.byandev.fundametalandroid13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        Kemudian untuk menghubungkan ViewPagerAdapter dengan ViewPager pada MainActivity
        Anda menggunakan kode berikut:
         */
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerAdapter

        // untuk menghubungkan TabLayout dan ViewPager, gunakan kode berikut:
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }
}
