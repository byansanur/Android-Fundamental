package com.byandev.submission2uiux.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
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
import com.byandev.submission2uiux.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.byandev.submission2uiux.utils.Constants.Companion.SEARCH_TIME_DELAY
import com.byandev.submission2uiux.utils.Responses
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentSearch : Fragment(R.layout.fragment_search) {

    lateinit var viewModel: SearchFragmentViewModel
    lateinit var adapter: SearchAdapter

    companion object {
        private val TAG = FragmentSearch::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRV()

        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("item", it)
            }
            findNavController().navigate(
                R.id.action_fragment_search_to_detailActivity,
                bundle
            )
        }

        var job: Job? = null
        etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                it?.let {
                    val editText = etSearch.toString()
                    if (editText.isNotEmpty()) {
                        viewModel.searchUser(editText)
                    }
                }
            }
        }

        viewModel.searchItem.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Responses.Success -> {
                    // hideLayout Method is here
                    hideLayoutProgressBar()
                    response.data?.let { searchResponse ->
                        adapter.differ.submitList(searchResponse.items.toList())
                        val totalPages = searchResponse.total_count / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.searchNewsPage == totalPages
                        if (isLastPage) {
                            rvListUser.setPadding(0,0,0,0)
                        }

                    }
                }
                is Responses.Error -> {
                    hideLayoutProgressBar()
                    response.message?.let { message ->
                        Snackbar.make(view, "An Error: $message", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Responses.Loading -> {
                    showLayoutProgressBar()
                }
            }
        })
    }

    private fun hideLayoutProgressBar() {
        pbLoading.visibility = View.GONE
        isLoading = false
    }

    private fun showLayoutProgressBar() {
        pbLoading.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
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
                viewModel.searchUser(etSearch.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }


    private fun setupRV() {
        adapter = SearchAdapter()
        rvListUser.apply {
            adapter = adapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@FragmentSearch.scrollListener)
        }
    }
}