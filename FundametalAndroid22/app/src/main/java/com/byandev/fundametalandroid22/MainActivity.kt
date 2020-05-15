package com.byandev.fundametalandroid22

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import androidx.work.Data.Builder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //PeriodicWorkRequest untuk menjalankan task secara periodic, untuk membuatnya Anda menggunakan kode berikut:
    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOneTimeTask.setOnClickListener(this)

        btnPeriodicTask.setOnClickListener(this)
        btnCancelTask.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnOneTimeTask -> startOneTimeTask()
            R.id.btnPeriodicTask -> startPeriodicTask()
            R.id.btnCancelTask -> cancelPeriodicTask()
        }
    }

    private fun startOneTimeTask() {
        textStatus.text = getString(R.string.status)
        val data = Builder()
            .putString(MyWorker.EXTRA_CITY, editCity.text.toString()) // untuk menambahkan data untuk dikirimkan
            .build()
        //Constraint digunakan untuk memberikan syarat kapan task ini dieksekusi
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // require connect to internet
            .build()
        //OneTimeWorkRequest untuk menjalankan task sekali saja, untuk membuatnya Anda menggunakan kode berikut:
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data) // menerima hasil inputan
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(oneTimeWorkRequest)
        WorkManager.getInstance()
            .getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, androidx.lifecycle.Observer {
                // WorkInfo digunakan untuk mengetahui status task yang dieksekusi
                val status = it.state.name
                textStatus.append("\n" + status)
            })
    }

    private fun startPeriodicTask() {
        textStatus.text = getString(R.string.status)
        val data = Builder()
            .putString(MyWorker.EXTRA_CITY, editCity.text.toString())
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(periodicWorkRequest)
        WorkManager.getInstance()
            .getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this@MainActivity, androidx.lifecycle.Observer {
                // WorkInfo digunakan untuk mengetahui status task yang dieksekusi
                val status = it.state.name
                textStatus.append("\n" + status)
                btnCancelTask.isEnabled = false
                if (it.state == WorkInfo.State.ENQUEUED) {
                    btnCancelTask.isEnabled = true
                }
            })
    }

    private fun cancelPeriodicTask() {
        WorkManager.getInstance().cancelWorkById(periodicWorkRequest.id)
        // Kode di atas digunakan untuk membatalkan task berdasarkan id request.
    }
}
