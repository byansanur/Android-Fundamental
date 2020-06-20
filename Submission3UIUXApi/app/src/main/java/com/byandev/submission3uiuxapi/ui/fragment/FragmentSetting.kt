package com.byandev.submission3uiuxapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.byandev.submission3uiuxapi.R
import kotlinx.android.synthetic.main.fragment_setting.*


class FragmentSetting : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardLanguage.setOnClickListener(this)
        cardAbout.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cardLanguage -> intentToSettingLanguage()
            R.id.cardAbout -> bsForAboutMe()
        }
    }

    private fun bsForAboutMe() {
        Toast.makeText(context, "Bian", Toast.LENGTH_SHORT).show()
    }

    private fun intentToSettingLanguage() {
        Toast.makeText(context, "Language", Toast.LENGTH_SHORT).show()
    }
}