package com.byandev.fundametalandroid5

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.byandev.fundametalandroid4.Hero
import com.byandev.fundametalandroid4.HeroAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: HeroAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataDescription: Array<String>
    private lateinit var dataPhoto: TypedArray
    private var heroes = arrayListOf<Hero>()
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lv_list)
        adapter = HeroAdapter(this)
        listView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        // ketika activity pertama kali dimulai
        prepare()
        addItem()
        // ketika item list diklik
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(this@MainActivity, heroes[position].name, Toast.LENGTH_SHORT).show()
        }

    }

    // Metode ini digunakan untuk memasukan data data ke arraylist supaya bisa diproses oleh adapter.
    private fun addItem() {
        for (position in dataName.indices) {
            val hero = Hero(
                    dataPhoto.getResourceId(position,-1),
                    dataName[position],
                    dataDescription[position]
            )
            heroes.add(hero)
        }
        adapter.heroes = heroes
    }

    // Metode prepare digunakan untuk inisiasi setiap data.
    // Di sini kita memanggil array yang tadi sudah dibuat pada berkas strings.xml.
    private fun prepare() {
        dataName = resources.getStringArray(R.array.data_name)
        dataDescription = resources.getStringArray(R.array.data_description)
        dataPhoto = resources.obtainTypedArray(R.array.data_photo)
    }
}
