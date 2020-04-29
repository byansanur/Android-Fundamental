package com.byandev.fundametalandroid2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvResult: TextView
    companion object {
        private const val REQUEST_CODE = 100
    }

    private var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnMove: Button = findViewById(R.id.btn_move_activity)
        btnMove.setOnClickListener(this)
        val btnMoveWithDataActivity: Button = findViewById(R.id.btn_move_activity_data)
        btnMoveWithDataActivity.setOnClickListener(this)
        val btnMoveWithObject:Button = findViewById(R.id.btn_move_activity_object)
        btnMoveWithObject.setOnClickListener(this)
        val btnDialPhone:Button = findViewById(R.id.btn_dial_number)
        btnDialPhone.setOnClickListener(this)

        val btnMoveForResult:Button = findViewById(R.id.btn_move_for_result)
        btnMoveForResult.setOnClickListener(this)
        tvResult = findViewById(R.id.tv_result);
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_move_activity -> {
                val move = Intent(context, MoveActivity::class.java)
                startActivity(move)
            }
            R.id.btn_move_activity_data -> {
                val moveWithData = Intent(context, MoveWithDataActivity::class.java)
                moveWithData.putExtra(MoveWithDataActivity.EXTRA_NAME, "Bians")
                moveWithData.putExtra(MoveWithDataActivity.EXTRA_AGE, 22)
                startActivity(moveWithData)
            }
            R.id.btn_move_activity_object -> {
                val person = Person(
                        "DicodingAcademy",
                        5,
                        "academy@dicoding.com",
                        "Bandung"
                )
                val moveWithObjectIntent = Intent(context, MoveWithObjectActivity::class.java)
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)
                startActivity(moveWithObjectIntent)
            }
            R.id.btn_dial_number -> {
                val phoneNumber = "081210841382"
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialPhoneIntent)
            }
            R.id.btn_move_for_result -> {
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                startActivityForResult(moveForResultIntent, REQUEST_CODE)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == MoveForResultActivity.RESULT_CODE) {
                val selectedValue = data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
                tvResult.text = "Hasil request : $selectedValue"
            }
        }
    }
}
