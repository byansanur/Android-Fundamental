package com.byandev.fundametalandroid17

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {

    companion object {
        private val TAG = MyBoundService::class.java.simpleName
    }

    private var mBinder = MyBinder()
    private val startTime = System.currentTimeMillis()

    /*
    Kode di bawah ini adalah kelas yang dipanggil di metode onServiceConnected untuk memanggil kelas service.
    Fungsinya untuk mengikat kelas service. Kelas MyBinder yang diberi turunan kelas Binder,
    mempunyai fungsi untuk melakukan mekanisme pemanggilan prosedur jarak jauh.
     */
    internal inner class MyBinder : Binder() {
        val getService: MyBoundService = this@MyBoundService
    }

    private var mTimer: CountDownTimer = object : CountDownTimer(100000, 1000) {
        /*
        Ketika CountDownTimer dijalankan, countdown timer akan berjalan sampai 100.000 milisecond
        atau 100 detik. Intervalnya setiap 1.000 milisecond atau 1 detik akan menampilkan log.
         */
        override fun onTick(l: Long) {
            val elapsedTime = System.currentTimeMillis() - startTime
            Log.d(TAG, "onTick: $elapsedTime")
        }
        override fun onFinish() {
        }
    }

    override fun onCreate() {
        // Metode onCreate() dipanggil ketika memulai pembentukan kelas MyBoundService.
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }


    override fun onBind(intent: Intent): IBinder? {
        // Setelah onCreate() terjadi maka akan lanjut ke onBind().
        Log.d(TAG, "onBind: ")
        mTimer.start()
        return mBinder
        // Pada metode onBind(), service akan berjalan dan diikatkan atau ditempelkan
        // dengan activity pemanggil. Pada metode ini juga, mTimer akan mulai berjalan.
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        /*
        Metode onDestroy() yang ada di MyBoundService ini berfungsi untuk melakukan penghapusan kelas
        MyBoundService dari memori. Jadi setelah service sudah terlepas dari kelas MainActivity,
        kelas MyBoundService juga terlepas dari memori android.
         */
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        mTimer.cancel()
        return super.onUnbind(intent)
        /*
        Kode di atas berfungsi untuk melepaskan service dari activity pemanggil.
        Kemudian setelah metode onUnBind dipanggil, maka ia akan memanggil metode onDestroy()
         */
    }

    override fun onRebind(intent: Intent) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }
}
