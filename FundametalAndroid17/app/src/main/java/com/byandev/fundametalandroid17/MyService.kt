package com.byandev.fundametalandroid17

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    companion object {
        internal val TAG = MyService::class.java
    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*
        Pada metode tersebut kita menjalankan sebuah background process
        untuk melakukan simulasi proses yang sulit. Dan ia berjalan secara asynchronous.
         */
        Log.d(TAG.toString(), "Service dijalankan...") // first time to launch
        GlobalScope.launch {
            delay(3000) // wait 3 second
            stopSelf()  // berfungsi untuk  memberhentikan atau mematikan MyService dari sistem Android.
            Log.d(TAG.toString(), "Service dihentikan") // second to launch
        }
        /*
        Note !!!
        Kekurangan dari service tipe ini adalah ia tak menyediakan background thread diluar
        ui thread secara default. Jadi tiada cara lainnya selain membuat thread secara sendiri.
         */
        return START_STICKY
        /*
        Note !!!
        START_STICKY menandakan bahwa bila service tersebut dimatikan oleh sistem Android
        karena kekurangan memori, ia akan diciptakan kembali jika sudah ada memori yang bisa digunakan.
         Metode onStartCommand() juga akan kembali dijalankan.
         */
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG.toString(), "onDestroy")
    }
}
