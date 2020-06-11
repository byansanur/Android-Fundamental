package com.byandev.fundametalandroid32

import android.media.SoundPool
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SoundPool
    private var soundId:Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = SoundPool.Builder()
                .setMaxStreams(10)
                .build()

        sp.setOnLoadCompleteListener { soundPool, sampleId, status ->
            if (status == 0) {
                spLoaded = true
            } else Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show()
        }

        soundId = sp.load(this, R.raw.clinking_glasses, 1)

        btn_soundpool.setOnClickListener {
            if (spLoaded) {
                sp.play(soundId, 1f,1f,0,0,1f)
            }
        }
    }
}