package com.byandev.fundametalandroid21

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat

@Suppress("SameParameterValue")
class GetCurrentWeatherJobService : JobService() {

    companion object {
        private val TAG = GetCurrentWeatherJobService::class.java

        // isi nama kota
//        internal const val CITY = "Jakarta"

        // isi dengan api key yang sudah di daftarkan pada OpenWeatherMap
//        internal const val APP_ID = "fb161587654f69bd97613e2be8c32a25"
        //Job Scheduler hanya bisa dijalankan pada API > 21.

    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG.toString(), "onStopJob()")
        return true
        /*
        Metode onStopJob akan dijalankan jika job sudah berjalan,
        akan tetapi kondisinya kemudian tidak terpenuhi.
        Kita dapat mengembalikan nilai true jika kita ingin menjadwalkan kembali job tersebut.
         */
    }

    /*
    Metode onStartJob adalah metode yang akan dipanggil ketika scheduler berjalan.
    Sedangkan metode onStopJob akan dipanggil ketika job sedang berjalan akan tetapi belum selesai
    dikarenakan kondisi nya tidak terpenuhi.
     */

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG.toString(), "onStartJob()")
        getCurrentWeather(params)
        return true

    }

    private fun getCurrentWeather(params: JobParameters?) {
        /*
        Di sini kita menggunakan AsyncHttpClient, berarti kita akan menggunakan
        client yang bertanggung jawab untuk koneksi data dan sifatnya adalah asynchronous.

        Target dari url webservice yang akan diakses adalah
        http://api.openweathermap.org/data/2.5/weather?q="+CITY+"&appid="+APP_ID.
         */

        val city:String = resources.getString(R.string.city)
        val appId:String = resources.getString(R.string.APP_ID)

        Log.d(TAG.toString(), "getCurrentWeather: Mulai.....")
        val client = AsyncHttpClient()
        val url = "http://api.openweathermap.org/data/2.5/weather?q=$city&appid=$appId"
        Log.d(TAG.toString(), "getCurrentWeather: $url")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            )
            {
                val result = String(responseBody)
                Log.d(TAG.toString(), result)
                try {
                    val responseObject = JSONObject(result)

                    val currentWeather = responseObject.getJSONArray("weather").getJSONObject(0).getString("main")
                    val description = responseObject.getJSONArray("weather").getJSONObject(0).getString("description")
                    val tempInKelvin = responseObject.getJSONObject("main").getDouble("temp")
                    val tempInCelsius = tempInKelvin - 273

                    val temperature = DecimalFormat("##.##").format(tempInCelsius)

                    val title = "Current Weather"

                    val message = "$currentWeather, $description with $temperature celsius"
                    val notifId = 100

                    showNotification(applicationContext, title, message, notifId)

                    Log.d(TAG.toString(), "onSuccess: Selesai.....")

                    // ketika proses selesai, maka perlu dipanggil jobFinished dengan parameter false;
                    jobFinished(params, false)

                } catch (e: Exception) {
                    Log.d(TAG.toString(), "onSuccess: Gagal.....")
                    // ketika terjadi error, maka jobFinished diset dengan parameter true.
                    // Yang artinya job perlu di reschedule
                    jobFinished(params, true)
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable?
            )
            {
                Log.d(TAG.toString(), "onFailure: Gagal.....")

                // ketika proses gagal, maka jobFinished diset dengan parameter true.
                // Yang artinya job perlu di reschedule
                jobFinished(params, true)
            }

        })
    }

    private fun showNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int
    )
    {
        val channelId = "Channel_1"
        val channelName = "Job scheduler channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_replay_black_24dp)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.black))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }
}