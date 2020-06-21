package com.byandev.submission2uiux.ui

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.byandev.submission2uiux.BuildConfig
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SaveDataTheme
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    private var switchTheme: Switch? = null
    private var switchAlarm: Switch? = null
    private lateinit var saveDataTheme: SaveDataTheme

    private var mNotificationManager: NotificationManager? = null

    companion object {
        private const val NOTIFICATION_ID = 0
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        saveDataTheme = SaveDataTheme(this)
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        switchTheme = findViewById<View>(R.id.swTheme) as Switch?
        switchAlarm = findViewById<View>(R.id.swNotification) as Switch?

        if (saveDataTheme.loadModeState() == true) {
            switchTheme?.isChecked = true
        }

        card1.setOnClickListener {
            val i = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(i)
        }

        tvVersion.text = resources.getString(R.string.version) +" "+ BuildConfig.VERSION_NAME

        setSwitch()

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    }

    private fun setSwitch() {
        switchTheme?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveDataTheme.setDarkModeState(true)
                resetApp()
            } else {
                saveDataTheme.setDarkModeState(false)
                resetApp()
            }
        }
        switchAlarm?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                deliverNotification(this)
                Toast.makeText(this, "Alarm on", Toast.LENGTH_SHORT).show()
            } else {
                mNotificationManager?.cancelAll()
                Toast.makeText(this, "Alarm off", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun resetApp() {
        val i = Intent(applicationContext, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        finish()
    }

    fun createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifies every 15 minutes to stand up and walk"
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

    fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, SettingsActivity::class.java)

        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_emoji_people_24)
            .setContentTitle("Hei...")
            .setContentText("You should stand up and walk around now!")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        mNotificationManager?.notify(NOTIFICATION_ID, builder.build())

    }

}
