package com.byandev.fundametalandroid1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        private const val STATE_RESULT = "state_result"
    }

    // saving state when device rotate view
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }

    private lateinit var edtWidth: EditText
    private lateinit var edtLength: EditText
    private lateinit var edtHeight: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT) as String
            tvResult.text = result
        }

        edtHeight = findViewById(R.id.edt_height)
        edtLength = findViewById(R.id.edt_length)
        edtWidth = findViewById(R.id.edt_width)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)
        tvResult.visibility = View.GONE

        btnCalculate.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_calculate) {
            val inputLength = edtLength.text.toString().trim()
            val inputWidth = edtWidth.text.toString().trim()
            val inputHeight = edtHeight.text.toString().trim()

            var isEmptyFields = false

            when{
                inputLength.isEmpty() -> {
                    isEmptyFields = true
                    edtLength.error = "Field tidak boleh kosong"
                }
                inputWidth.isEmpty() -> {
                    isEmptyFields = true
                    edtWidth.error = "Field tidak boleh kosong"
                }
                inputHeight.isEmpty() -> {
                    isEmptyFields = true
                    edtHeight.error = "Field tidak boleh kosong"
                }
                isEmptyFields.not() -> {
                    val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                    tvResult.text = volume.toString()
                    tvResult.visibility = View.VISIBLE
                }
            }

        }
    }
}
