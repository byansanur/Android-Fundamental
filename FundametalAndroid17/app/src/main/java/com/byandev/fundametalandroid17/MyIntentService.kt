package com.byandev.fundametalandroid17

import android.app.IntentService
import android.content.Intent
import android.util.Log

/*
Skenarionya adalah sebagai berikut:
1.  Kita menjalankan IntentService tersebut dengan sebuah obyek Intent dari MainActivity dengan membawa data,
    dalam konteks ini adalah nilai integer yang menentukan berapa lama background process akan dijalankan.
2.  MyIntentService dijalankan dan kemudian memproses obyek Intent yang dikirimkan untuk menjalankan background.
3.  Seperti sifatnya, IntentService tidak perlu mematikan dirinya sendiri.
    Secara otomatis ketika proses yang dilakukan selesai, maka IntentService berhenti dengan sendirinya.
 */

class MyIntentService : IntentService("MyIntentService") {

    /*
    MyIntentService dijalankan. Service tersebut akan melakukan pemrosesan obyek Intent
    yang dikirimkan dan menjalankan suatu proses yang berjalan di background.
     */

    companion object {
        internal const val EXTRA_DURATION = "extra_duration"
        private val TAG = MyIntentService::class.java
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG.toString(), "onHandleInent: Start...")
        val duration = intent?.getLongExtra(EXTRA_DURATION, 0) as Long
        try {
            Thread.sleep(duration)
            Log.d(TAG.toString(), "onHandleIntent: End...")
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()

        }
    }
    /*
    Kode di atas akan dijalankan pada thread terpisah secara asynchronous.
    Jadi kita tak lagi perlu membuat background thread seperti pada service sebelumnya.

    Terakhir, IntentService tak perlu mematikan dirinya sendiri.
    Service ini akan berhenti dengan sendirinya ketika sudah selesai menyelesaikan tugasnya.
     */

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG.toString(), "onDestroy")
    }

}
