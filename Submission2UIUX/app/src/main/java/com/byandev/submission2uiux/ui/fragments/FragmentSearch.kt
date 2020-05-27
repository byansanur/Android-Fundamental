package com.byandev.submission2uiux.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.ui.MainActivity
import com.byandev.submission2uiux.ui.adapter.SearchAdapter
import com.byandev.submission2uiux.ui.viewModel.SearchFragmentViewModel
import com.byandev.submission2uiux.utils.Constants.Companion.SEARCH_TIME_DELAY
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentSearch : Fragment(R.layout.fragment_search) {

    lateinit var viewModel: SearchFragmentViewModel
    lateinit var adapter: SearchAdapter
    private lateinit var rvListUser: RecyclerView
    private lateinit var etSearch: EditText


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        rvListUser = view.findViewById(R.id.rvListUser)
        etSearch = view.findViewById(R.id.etSearch)

        rvAdapters()
        listener()
    }

    private fun rvAdapters() {
        rvListUser.setHasFixedSize(true)
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        val linearLayoutManager = LinearLayoutManager(context)
        rvListUser.layoutManager = linearLayoutManager
        rvListUser.adapter = adapter
    }

    private fun listener() {
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("item", it)
            }
            findNavController().navigate(
                R.id.action_fragment_search_to_detailActivity,
                bundle
            )
        }
        viewModel.getSearch().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        })

        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.setSearch(editable.toString())
                        showLoading(true)
                    }
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) pbLoading.visibility = View.VISIBLE
        else pbLoading.visibility = View.GONE
    }
}