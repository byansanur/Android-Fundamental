package com.byandev.fundametalandroid22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.work.*
import androidx.work.Data.Builder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOneTimeTask.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnOneTimeTask -> startOneTimeTask()
        }
    }

    private fun startOneTimeTask() {
        textStatus.text = getString(R.string.status)
        val data = Builder()
            .putString(MyWorker.EXTRA_CITY, editCity.text.toString())
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(oneTimeWorkRequest)
        WorkManager.getInstance()
            .getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, androidx.lifecycle.Observer {
                val status = it.state.name
                textStatus.append("\n" + status)
            })
    }
}
