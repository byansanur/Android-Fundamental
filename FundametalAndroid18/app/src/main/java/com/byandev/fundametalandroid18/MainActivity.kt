package com.byandev.fundametalandroid18

import android.Manifest.permission.RECEIVE_SMS
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val SMS_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_permission.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when {
            v?.id == R.id.btn_permission -> PermissionManager.check(this, RECEIVE_SMS, SMS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                    Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this,"Sms receiver pemission di tolak",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
