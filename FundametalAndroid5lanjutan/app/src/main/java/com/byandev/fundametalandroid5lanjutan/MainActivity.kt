package com.byandev.fundametalandroid5lanjutan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnSetValue: Button
    private lateinit var tvText: TextView

    private var names = ArrayList<String>()
    private lateinit var imgPreview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // case 1 error : lateinit property btnSetValue has not been initialized
        // the solution is that "btnSetValue = findViewById(R.id.btn_set_value)"
        btnSetValue = findViewById(R.id.btn_set_value)
        tvText = findViewById(R.id.tv_text)
        btnSetValue.setOnClickListener(this)


        // case 2 error :
        // IndexOutOfBoundsException. Collection names memiliki 3 item. Karena sebuah collection dimulai dari 0,
        // maka item terakhirnya berada pada indeks ke 2.
        // Ketika kita hendak mengambil data ke-3, maka IndexOutOfBoundsException akan dibangkitkan.
        names.add("Narenda Wicaksono")
        names.add("Kevin")
        names.add("Yoza")

        // case 3 error :
        // out of memory dapat terjadi karena ukuran gambar yang dimuat melebihi memori yang tersedia untuk menjalankan aplikasi.
        // solusi :
        // Solusinya adalah perkecil ukuran gambar sebelum ditampilkan. Gunakan library Glide untuk mengecilkan gambar.
        imgPreview = findViewById(R.id.img_preview)
//        imgPreview.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fronalpstock_big))
        Glide.with(this)
                .load(R.drawable.fronalpstock_big)
                .into(imgPreview)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_set_value) {
            val name = StringBuilder()
            // solution case 2 : Mengganti nilai maksimal perulangan dari 3 menjadi 2 berikut pada proses perulangan for.
            for (i in 0..2) {
                name.append(names[i]).append("\n")
            }
            tvText.text = name.toString()
        }
    }
}
