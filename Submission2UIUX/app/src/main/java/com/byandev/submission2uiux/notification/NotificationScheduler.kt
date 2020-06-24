package com.byandev.submission2uiux.notification

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import com.byandev.submission2uiux.R
import java.util.*

@Suppress("DEPRECATION")
class NotificationScheduler {

    companion object {

        private const val DAILY_REMINDER_REQUEST_CODE = 100
        private const val TAG = "NotificationScheduler"
        private lateinit var notification: Notification
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"

        private fun createChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val importance = NotificationManager.IMPORTANCE_HIGH
                val notificationChannel =
                    NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
                notificationChannel.enableVibration(true)
                notificationChannel.setShowBadge(true)
                notificationChannel.enableLights(true)

                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        fun cancelReminder(
            context: Context,
            cls: Class<*>?
        ) {
            // Disable a receiver
            val receiver = ComponentName(context, cls!!)

            val pm = context.packageManager
            pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )

            val intent1 = Intent(context, cls)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                DAILY_REMINDER_REQUEST_CODE,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val am =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(pendingIntent)

            pendingIntent.cancel()

        }

        fun setReminder(
            context: Context,
            cls: Class<*>?
        ) {
            val calendar = Calendar.getInstance()
            val setCalendar = Calendar.getInstance()
            setCalendar[Calendar.HOUR_OF_DAY] = 9
            setCalendar[Calendar.MINUTE] = 0
            setCalendar[Calendar.SECOND] = 0
            Log.d(TAG, "setReminder: $setCalendar")

            // cancel already scheduled reminders
            cancelReminder(context, cls)
            if (setCalendar.before(calendar)) setCalendar.add(Calendar.DATE, 1)

            // Enable a receiver
            val receiver = ComponentName(context, cls!!)

            val pm = context.packageManager
            pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )

            val intent1 = Intent(context, cls)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                DAILY_REMINDER_REQUEST_CODE,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val am =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                setCalendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        fun showNotification(
            context: Context,
            cls: Class<*>?,
            title: String?,
            content: String?
        ) {
            createChannel(context)

            val alarmSound =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val notificationIntent = Intent(context, cls)
            notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(cls)
            stackBuilder.addNextIntent(notificationIntent)

            val pendingIntent = stackBuilder.getPendingIntent(
                DAILY_REMINDER_REQUEST_CODE,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val res = context.resources

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                notification = Notification.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .setSmallIcon(R.drawable.ic_baseline_timer_24)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .build()

            } else {

                notification = Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .setSmallIcon(R.drawable.ic_baseline_timer_24)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .build()

            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(DAILY_REMINDER_REQUEST_CODE, notification)

        }

    }

}