package com.byandev.submission2uiux.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.byandev.submission2uiux.alarm.AlarmReceiver
import java.util.*

class NotificationUtils {

    fun setNotification(timeInMilliSecond: Long, activity: Activity) {

        if (timeInMilliSecond > 0) {

            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java)
            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSecond)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMilliSecond

            val pendingIntent = PendingIntent.getBroadcast(
                activity,
                0,
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            Toast.makeText(activity, "alarm started", Toast.LENGTH_SHORT).show()

        }

    }

    fun resetNotification(timeInMilliSecond: Long, activity: Activity) {
        if (timeInMilliSecond > 0) {

            val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(activity, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                activity,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
            Toast.makeText(activity, "alarm stopped", Toast.LENGTH_SHORT).show()

        }
    }
}
