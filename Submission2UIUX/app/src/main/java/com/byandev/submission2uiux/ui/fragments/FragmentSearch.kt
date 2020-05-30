package com.byandev.submission2uiux.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.ui.MainActivity
import com.byandev.submission2uiux.ui.adapter.SearchAdapter
import com.byandev.submission2uiux.ui.viewModel.search.SearchFragmentViewModel
import com.byandev.submission2uiux.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.byandev.submission2uiux.utils.Constants.Companion.SEARCH_TIME_DELAY
import com.byandev.submission2uiux.utils.Resource
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentSearch : Fragment() {

    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        searchAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("search", it)
            }
            findNavController().navigate(
                R.id.action_fragmentSearch_to_detailActivity,
                bundle
            )
        }

        layoutDataKosong.visibility = View.VISIBLE

        textChangeListener()

        sweepRefresh.setOnRefreshListener {
            textChangeListener()
        }


        viewModel.searchUsers.observe(viewLifecycleOwner, Observer { it ->
            when(it) {
                is Resource.Success -> {
                    showLoading(false)
                    layoutDataKosong.visibility = View.GONE
                    sweepRefresh.isRefreshing = false
                    it.data?.let {
                        searchAdapter.differ.submitList(it.items.toList())
                        val totalPage = it.total_count!! / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.searchUsersPage == totalPage
                        if (isLastPage) {
                            rvListUser.setPadding(0,10,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        showLoading(false)
                        layoutDataKosong.visibility = View.VISIBLE
                        tvNoData.text = getString(R.string.no_internet_title)
                        tvNoDataDesc.text = getString(R.string.no_internet_description)
                        sweepRefresh.isRefreshing = false
                        Toast.makeText(activity, "An error: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showLoading(true)
                    sweepRefresh.isRefreshing = true
                }
            }
        })

        fbSettingsTranslate.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            context?.startActivity(intent)
        }

    }

    private fun textChangeListener() {
        sweepRefresh.isRefreshing = false
        var job: Job? = null
        etSearch.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchFetch(editable.toString())
                        searchAdapter.notifyDataSetChanged()
                        layoutDataKosong.visibility = View.GONE
                    } else layoutDataKosong.visibility = View.VISIBLE
                }
            }
        }
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            sweepRefresh.isRefreshing = false
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firsVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && ! isLastPage
            val isAtLastItem = firsVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firsVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginante = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginante) {
                viewModel.searchFetch(etSearch.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
            sweepRefresh.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        searchAdapter.notifyDataSetChanged()

        rvListUser.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(context, 2)
            addOnScrollListener(this@FragmentSearch.scrollListener)
        }
    }


    private fun showLoading(state: Boolean) {
        if (state) pbLoading.visibility = View.VISIBLE
        else pbLoading.visibility = View.GONE
    }
}

