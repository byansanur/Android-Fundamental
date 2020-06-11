package com.byandev.stacknotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /*
    Variabel idNotification adalah indeks yang akan kita gunakan untuk mengakses list stackNotif.
    Semua notifikasi yang kita kirimkan akan kita masukkan ke dalam variabel stackNotif tersebut.

    Mengapa perlu disimpan? Tujuannya agara kita tahu berapa jumlah notifikasi yang kita tampilkan.
    Jumlah tersebut akan kita gunakan sebagai parameter untuk menggunakan stack notifikasi.
     */
    private var idNotification = 0
    private val stackNotif = ArrayList<NotificationItem>()

    companion object {
        private const val CHANNEL_NAME= "byandev channel"
        private const val GROUP_KEY_EMAILS = "group_key_emails"
        private const val NOTIFICATION_REQUEST_CODE = 200
        private const val MAX_NOTIFICATION = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSend.setOnClickListener {
            val sender = edtSender.text.toString()
            val message = edtMessage.text.toString()
            if (sender.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Data harus di isi", Toast.LENGTH_SHORT).show()
            } else {
                /*
                Di sini kita membuat obyek baru NotificationItem yang kemudian kita tambahkan
                pada list stackNotif. Setelah itu kita jalankan metode sendNotif().
                Proses diakhiri dengan menaikkan variable idNotif dengan menggunakan kode idNotif++.

                Menaikkan nilai idNotif sangatlah penting karena dia digunakan sebagai indeks untuk
                mengakses item dari stackNotif yang terakhir kali dimasukkan.
                 */
                stackNotif.add(NotificationItem(idNotification,sender,message))
                sendNotif()
                idNotification++
                edtSender.setText("")
                edtMessage.setText("")

                // close keyboard if button onClick
                val methodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                methodManager.hideSoftInputFromWindow(edtMessage.windowToken,0)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        /*
        Metode ini akan dijalankan ketika ada intent baru (pending intent) yang dikirimkan
        ke dalam activity. Dengan menjalankan stackNotif.clear() dan idNotif=0
        maka kita menghapus semua data pada list stackNotif dan
        mengembalikan indeks idNotif menjadi 0.
        Alhasil, semuanya di-reset kembali ketika user menekan notifikasi yang ada.
         */
        super.onNewIntent(intent)
        stackNotif.clear()
        idNotification = 0
    }

    private fun sendNotif() {
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val largeIcon =
            BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_notifications_active_24)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mBuilder: NotificationCompat.Builder

        // cek idNotification <= MAX_NOTIFICATION
        // Di dalam metode sendNotif() terdapat percabangan if yang menghitung apakah index idNotif
        // sudah melewati nilai dari maxNotif.
        val CHANNEL_ID= "channel_01"
        if (idNotification < MAX_NOTIFICATION) {
            //Jika belum, maka notifikasi yang akan ditampilkan adalah notifikasi
            //biasa yang ada pada baris ini:
            mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("New Email from " + stackNotif[idNotification].sender)
                .setContentText(stackNotif[idNotification].message)
                .setSmallIcon(R.drawable.ic_baseline_mail_24)
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_KEY_EMAILS)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            /*
            Dan ketika indeks idNotif sudah lebih besar dari nilai maxNotif ,
            maka notifikasinya akan dikelompokkan. Hanya beberapa notifikasi terakhir
            yang akan ditampilkan. Perhatikanlah kode berikut ini:
             */
            val inboxStyle = NotificationCompat.InboxStyle()
                // Text yang akan ditampilkan ada pada obyek InboxStyle.
                // Perhatikan pada kode addLine. Pada kode tersebut ada 2 buah addLine.
                // Yang pertama:
                .addLine("New Email from " + stackNotif[idNotification].sender)
                // Dan yang kedua :
                /*
                Kedua addLine di atas mengindikasikan bahwa hanya 2 baris yang akan ditampilkan
                pada kelompok notifikasi.
                Baris pertama adalah data sender dari stackNotif, di mana nilai indeksnya
                diperoleh dari idNotif. Sementara itu,
                baris kedua adalah sender dari stackNotif di mana indeksnya diperoleh dari
                operasi idNotif minus satu.

                Jika kita ingin menampilkan lebih dari 2 notifikasi,
                cukup tambahkan addLine lagi di bawahnya.
                 */
                .addLine("New Email from " + stackNotif[idNotification - 1].sender)
                .setBigContentTitle("$idNotification new emails")
                .setSummaryText("mail@byandev")
            mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("$idNotification new emails")
                .setContentText("mail@dicoding.com")
                .setSmallIcon(R.drawable.ic_baseline_mail_24)
                .setGroup(GROUP_KEY_EMAILS)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
            /*
            Yang harus diperhatikan adalah metode setGroup() dan setGroupSummary(true).
            Kedua metode inilah yang akan menjadikan notifikasi menjadi sebuah bundle notification.
             */
        }
        /*
       Untuk android Oreo ke atas perlu menambahkan notification channel
       Materi ini akan dibahas lebih lanjut di modul extended
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()

        // berikut  kode untuk menampilkan notifikasi.
        mNotificationManager.notify(idNotification, notification)
    }
}
