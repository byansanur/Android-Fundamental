package com.byandev.fundametalandroid18.broadcastEvent

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log


class DownloadService : IntentService("DownloadService") {

    companion object {
        val TAG = DownloadService::class.java
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG.toString(), "Download service dijalankan")

        if (intent != null) {
            try {
                Thread.sleep(5000)
            }catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val notifyFInishIntent = Intent(Main2Activity.ACTION_DOWNLOAD_STATUS)
            sendBroadcast(notifyFInishIntent)
        }
    }


}
