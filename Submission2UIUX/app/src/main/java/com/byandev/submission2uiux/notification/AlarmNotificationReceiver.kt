package com.byandev.submission2uiux.notification

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.ui.MainActivity

class AlarmNotificationReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmNotificationReceiver"
    }

    @SuppressLint("LongLogTag")
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        if(intent.action != null) {
            if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED")
                NotificationScheduler.setReminder(
                    context,
                    AlarmNotificationReceiver::class.java
                )
                return
            }
        }

        Log.d(TAG, "onReceive: ")

        val title: String = context.getString(R.string.app_name)
        val content: String = context.getString(R.string.content_notif)

        NotificationScheduler.showNotification(context, MainActivity::class.java, title,content)
    }
}
