package com.byandev.fundametalandroid26

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val REQUEST_CODE =100
    }
    private lateinit var mUserPreferences: UserPreferences
    private var isPreferencesEmpty = false
    private lateinit var userModel: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "My User Preferences"

        mUserPreferences = UserPreferences(this)
        showExistingPreference()

        btn_save.setOnClickListener(this)
    }

    /*
    Menampilkan data preference yang ada
     */
    private fun showExistingPreference() {
        userModel = mUserPreferences.getUser()
        populateView(userModel)
        checkForm(userModel)
    }

    /*
    Set tampilan menggunakan data preferences
    */
    private fun populateView(userModel: UserModel) {
        tv_name.text = if (userModel.name.toString().isEmpty()) "Tidak Ada" else userModel.name
        tv_age.text = if (userModel.age.toString().isEmpty()) "Tidak Ada" else userModel.age.toString()
        tv_is_love_mu.text = if (userModel.isLove) "Ya" else "Tidak"
        tv_email.text = if (userModel.email.toString().isEmpty()) "Tidak Ada" else userModel.email
        tv_phone.text = if (userModel.phoneNumber.toString().isEmpty()) "Tidak Ada" else userModel.phoneNumber
    }

    private fun checkForm(userModel: UserModel) {
        when {
            userModel.name.toString().isNotEmpty() -> {
                btn_save.text = getString(R.string.change)
                isPreferencesEmpty = false
            }
            else -> {
                btn_save.text = getString(R.string.save)
                isPreferencesEmpty = true
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_save) {
            val intent = Intent(this@MainActivity, AddUserActivity::class.java)
            when {
                isPreferencesEmpty -> {
                    intent.putExtra(AddUserActivity.EXTRA_TYPE_FORM, AddUserActivity.TYPE_ADD)
                    intent.putExtra("USER", userModel)
                }
                else -> {
                    intent.putExtra(AddUserActivity.EXTRA_TYPE_FORM, AddUserActivity.TYPE_EDIT)
                    intent.putExtra("USER", userModel)
                }
            }
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    /*
  Akan dipanggil ketika AddUserActivity ditutup
   */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == AddUserActivity.RESULT_CODE) {
                userModel = data?.getParcelableExtra(AddUserActivity.EXTRA_RESULT) as UserModel
                populateView(userModel)
                checkForm(userModel)
            }
        }
    }
}