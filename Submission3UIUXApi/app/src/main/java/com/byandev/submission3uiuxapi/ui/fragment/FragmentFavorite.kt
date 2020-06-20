package com.byandev.submission3uiuxapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.byandev.submission3uiuxapi.R
import com.byandev.submission3uiuxapi.ui.HomeActivity
import com.byandev.submission3uiuxapi.ui.adapter.SearchAdapter
import com.byandev.submission3uiuxapi.ui.viewModel.home.HomeViewModel


class FragmentFavorite : Fragment() {


    lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel


    }


}