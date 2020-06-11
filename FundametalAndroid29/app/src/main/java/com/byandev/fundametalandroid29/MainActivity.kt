package com.byandev.fundametalandroid29

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    companion object {
        // initialize for channel notification
        val NOTIFICATION_ID = 1
        var CHANNEL_ID = "channel_01"
        var CHANNEL_NAME = "byandev channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize notificationManager
        val mNotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // builder notification
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setLargeIcon(BitmapFactory.decodeResource(
                        resources,
                        R.drawable.ic_baseline_notifications_active_24)
                )
                .setContentTitle(resources.getString(R.string.content_title))
                .setContentText(resources.getString(R.string.content_text))
                .setSubText(resources.getString(R.string.subtext))

        // notification for android oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // initialize the channel
            val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_NAME
            mBuilder.setChannelId(CHANNEL_ID)

            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)
        /*
        Kode di atas digunakan untuk mengirim notifikasi sesuai dengan id yang kita berikan.
        Fungsi id di sini nanti juga bisa untuk membatalkan notifikasi yang sudah muncul.
         */

    }
}