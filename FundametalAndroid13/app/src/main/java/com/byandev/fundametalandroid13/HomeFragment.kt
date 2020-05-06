package com.byandev.fundametalandroid13

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    /*
    Di dalam fungsi tersebut Anda memasukkan parameter yang dikirimkan ke dalam Bundle
    sesuai dengan tipe datanya dengan format Key-Value, dengan ARG_SECTION_NUMBER bertindak
    sebagai key dan index sebagai value. Kemudian setArgument digunakan untuk mengirimkan
    data bundle tersebut ke fragment.
     */
    companion object {
        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kemudian untuk mendapatkan data yang dikirimkan lihat pada kode berikut:
        var index = 1
        if (getArguments() != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
        }
        section_label.text = "${getString(R.string.content_tab_text)} $index"
    }

}
