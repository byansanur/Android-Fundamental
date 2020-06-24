package com.byandev.submission2uiux.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SharedPref
import com.byandev.submission2uiux.ui.DetailActivity
import com.byandev.submission2uiux.ui.adapter.FollowersAdapter
import com.byandev.submission2uiux.ui.viewModel.followFollow.FollowersViewModel
import com.byandev.submission2uiux.utils.Constants.Companion.DETAIL_FOLLOW_DELAY
import com.byandev.submission2uiux.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.byandev.submission2uiux.utils.Resource
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentFollowers : Fragment() {


    private lateinit var viewModel: FollowersViewModel
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var sharedPref: SharedPref
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_follow, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPref = SharedPref(context)
        if (sharedPref.loadModeState() == true) {
            requireActivity().setTheme(R.style.DarkThem)
        } else {
            requireActivity().setTheme(R.style.AppTheme)
        }
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as DetailActivity).viewModelFollowers

        setupRv()

        layoutNoData.visibility = View.VISIBLE
        prepareData()

        sweep.setOnRefreshListener {
            prepareData()
        }

        viewModel.userFollowers.observe(viewLifecycleOwner, Observer { it ->
            when(it) {
                is Resource.Success -> {

                    layoutNoData.visibility = View.GONE
                    sweep.isRefreshing = false
                    it.data?.let {
                        if (it.isNullOrEmpty()) {
                            layoutNoData.visibility = View.VISIBLE
                            tvFollowNoData.text = getString(R.string.no_followers)
                            tvFollowNoDataDesc.text = getString(R.string.no_follow_desc)
                        } else {
                            followersAdapter.differ.submitList(it.toList())
                            val totalPage = QUERY_PAGE_SIZE + 2
                            isLastPage = viewModel.pagination == totalPage
                            if (isLastPage) {
                                rvListFollow.setPadding(0,10,0,0)
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->

                        layoutNoData.visibility = View.VISIBLE
                        tvFollowNoData.text = getString(R.string.no_internet_title)
                        tvFollowNoData.text = getString(R.string.no_internet_description)
                        sweep.isRefreshing = false
                        Toast.makeText(context, "An error: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {

                    sweep.isRefreshing = true
                }
            }
        })

    }

    private fun prepareData() {
        sweep.isRefreshing = false

        val username = (activity as DetailActivity).args
        val useName = username.search.login

        val job: Job? = null
        if (useName != null) {
            job?.cancel()
            MainScope().launch {
                delay(DETAIL_FOLLOW_DELAY)
                viewModel.followersFetch(useName)
                followersAdapter.notifyDataSetChanged()
                layoutNoData.visibility = View.GONE
            }
        } else layoutNoData.visibility = View.VISIBLE

    }



    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            sweep.isRefreshing = false
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firsVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && ! isLastPage
            val isAtLastItem = firsVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firsVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                val username = (activity as DetailActivity).args
                viewModel.followersFetch(username.search.login.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
            sweep.isRefreshing = false
        }
    }


    private fun setupRv() {
        followersAdapter = FollowersAdapter()
        followersAdapter.notifyDataSetChanged()

        rvListFollow.apply {
            adapter = followersAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(this@FragmentFollowers.scrollListener)
        }
    }

}