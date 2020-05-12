package com.byandev.fundametalandroid17

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // penghubung boundService
    private var mServiceBound = false
    private lateinit var mBoundService: MyBoundService

    /*
    Kode di bawah merupakan sebuah listener untuk menerima callback dari ServiceConnetion.
    Kalau dilihat ada dua callback, yakni ketika mulai terhubung dengan kelas service dan
    juga ketika kelas service sudah terputus.
     */
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }
    }

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
                val mStartServiceIntent = Intent(this@MainActivity, MyService::class.java)
                // menjalankan pada clas myService pada metode onStartCommand()
                startService(mStartServiceIntent)
                // note : karena kita akan menjalan kan service bukan pindah halaman
            }
            R.id.btn_start_intent_service -> {
                /*
                Service tersebut akan melakukan pemrosesan obyek Intent yang
                dikirimkan dan menjalankan suatu proses yang berjalan di background.
                 */
                val mStartIntentService = Intent(this, MyIntentService::class.java)
                mStartIntentService.putExtra(MyIntentService.EXTRA_DURATION, 5000L)
                startService(mStartIntentService)
            }
            R.id.btn_start_bound_service -> {
                val mBoundServiceIntent = Intent(this@MainActivity, MyBoundService::class.java)
                bindService(mBoundServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
                /*
                Pada kode di atas kita menggunakan bindService yang digunakan untuk memulai mengikat
                kelas MyBoundService ke kelas MainActivity.
                Sedangkan mBoundServiceIntent adalah sebuah intent eksplisit yang digunakan untuk
                menjalankan komponen dari dalam sebuah aplikasi.
                Sedangkan mServiceConnection adalah sebuah ServiceConnection berfungsi sebagai callback
                dari kelas MyBoundService.
                Kemudian ada juga BIND_AUTO_CREATE yang membuat sebuah service jika service tersebut belum aktif.
                 */
            }
            R.id.btn_stop_bound_service -> {
                /*
                Setelah service mulai terikat maka mTimer akan berjalan sesuai dengan yang ditentukan.
                Selanjutnya untuk mengakhiri MyBoundService yang masih terikat, bisa gunakan kode berikut:
                 */
                unbindService(mServiceConnection)
            }
        }
    }

    override fun onDestroy() {
        /*
        Kode onDestroy() seperti yang dijelaskan di metode sebelumnya,
        akan memanggil unBindService atau melakukan pelepasan service dari Activity.
         */
        super.onDestroy()
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
    }
}
