package com.byandev.fundametalandroid18.broadcastEvent

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.byandev.fundametalandroid18.R
import com.byandev.fundametalandroid18.broadcastSms.PermissionManager
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val ACTION_DOWNLOAD_STATUS = "download_status"
        private const val SMS_REQUEST_CODE = 101
    }

    private lateinit var  downloadService: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_permission.setOnClickListener(this)
        btn_download.setOnClickListener(this)

        downloadService = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d(DownloadService.TAG.toString(), "Download selesai")
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }

        }

        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)

        registerReceiver(downloadService, downloadIntentFilter)
    }

    override fun onClick(v: View?) {
        when {
            v?.id == R.id.btn_permission ->
                PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE)
            v?.id == R.id.btn_download -> {
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
                startService(downloadServiceIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadService)
    }
}
