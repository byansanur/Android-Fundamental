package com.byandev.soundpoolmediaplayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import java.io.IOException
import java.lang.ref.WeakReference

@Suppress("SameParameterValue", "DEPRECATION")
class MediaService : Service(), MediaPlayerCallback {

    private val s = MediaService::class.java.simpleName
    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false

    companion object {
        const val ACTION_CREATE = "com.byandev.soundpoolmediaplayer.mediaservice.create"
        const val ACTION_DESTROY = "com.byandev.soundpoolmediaplayer.mediaservice.destroy"
        const val PLAY = 0
        const val STOP = 1
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action != null) {
            when (action) {
                ACTION_CREATE -> if (mMediaPlayer == null) {
                    init()
                }
                ACTION_DESTROY -> if (mMediaPlayer?.isPlaying as Boolean) {
                    stopSelf()
                }
                else -> init()
            }
        }
        Log.d(s, "onStartCommand: ")
        return flags
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(s, "onBind: ")
        return mMessenger.binder
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            mMediaPlayer?.run{attributes}
        } else {
            mMediaPlayer?.run {setAudioStreamType(AudioManager.STREAM_MUSIC) }
        }
        // Lihat pada bagian di atas, kode tersebut berguna untuk memperbarui MediaPlayer.


        val afd = applicationContext.resources.openRawResourceFd(R.raw.music_satu)
        try {

            mMediaPlayer?.run { setDataSource(afd.fileDescriptor, afd.startOffset, afd.length) }

        } catch (e: IOException) {

            e.printStackTrace()

        }
        // Kode di atas berfungsi untuk mengambil file suara dalam folder R.raw, kemudian di masukkan ke dalam MediaPlayer.
        // Kode setDataSource berfungsi untuk memasukkan detail informasi dari asset atau musik yang akan diputar.

        mMediaPlayer?.setOnPreparedListener {
            isReady = true
            mMediaPlayer?.start()
            showNotif()
        }
        // Kemudian ketika MediaPlayer sudah disiapkan, maka akan menjalankan musik atau
        // asset yang sudah disiapkan sebelumnya dengan perintah start().

        mMediaPlayer?.setOnErrorListener { _, _, _ ->  false }
    }


    override fun onPlay() {
        if (!isReady) {
            /*
            Perintah prepareAsync() berfungsi untuk menyiapkan MediaPlayer jika belum disiapkan
            atau diperbarui. Ketika prepareAsync() dijalankan maka proses ini bersifat asynchronous.
            Ini untuk memastikan aplikasi tetap berjalan secara responsif.
             */
            mMediaPlayer?.prepareAsync()
        } else {
            if (mMediaPlayer?.isPlaying as Boolean) {
                mMediaPlayer?.pause()
            } else {
                mMediaPlayer?.start()
                showNotif()
            }
        }
    }

    override fun onStop() {
        if (mMediaPlayer?.isPlaying as Boolean || isReady) {
            mMediaPlayer?.stop()
            isReady = false
            stopNotif()
            // Fungsi mMediaPlayer.stop() digunakan untuk menghentikan MediaPlayer yang sedang berjalan (play).
        }
    }

    /**
     * Method incomingHandler sebagai handler untuk aksi dari onklik button di MainActivity
     */
    private val mMessenger = Messenger(IncomingHandler(this))

    internal class IncomingHandler(playerCallback: MediaPlayerCallback) : Handler() {
        private val mediaPlayerCallback: WeakReference<MediaPlayerCallback> = WeakReference(playerCallback)

        override fun handleMessage(msg: Message) {
            when(msg.what) {
                PLAY -> {
                    mediaPlayerCallback.get()?.onPlay()
                }
                STOP -> {
                    mediaPlayerCallback.get()?.onStop()
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private fun showNotif() {
        val CHANNEL_DEFAULT_IMPORTANCE = "Channel_Test"
        val ONGOING_NOTIFICATION_ID = 1

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        /*
        Intent pada notif tersebut menggunakan Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT.
        Flags tersebut berfungsi untuk memanggil activity yang sudah ada tanpa membuat activity baru dan menampilkannya.
         */

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle("TEST1")
            .setContentText("TEST2")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setTicker("TES3")
            .build()

        createChannel(CHANNEL_DEFAULT_IMPORTANCE)

        startForeground(ONGOING_NOTIFICATION_ID, notification)

    }

    private fun createChannel(CHANNELS_ID: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNELS_ID, "Battery", NotificationManager.IMPORTANCE_DEFAULT)
            channel.setShowBadge(false)
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun stopNotif() {
        stopForeground(false)
    }
}
