package com.byandev.submission2uiux.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.ui.DetailActivity
import com.byandev.submission2uiux.ui.adapter.FollowingAdapter
import com.byandev.submission2uiux.ui.viewModel.followFollow.FollowFollowViewModel
import kotlinx.android.synthetic.main.fragment_follow.*

class FragmentFollowing  : Fragment() {

    private lateinit var viewModel: FollowFollowViewModel
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as DetailActivity).viewModelFollow

        setupRv()
        prepareData()
        listener()

    }

    private fun listener() {
        layoutNoData.visibility = View.VISIBLE

//        viewModel.userFollowings.observe(viewLifecycleOwner, Observer { resource ->
//            when(resource) {
//                is Resource.Success -> {
//                    showLoading(false)
//                    layoutNoData.visibility = View.GONE
//                    prepareData()
////                    sweepRefresh.isRefreshing = false
//                    resource.data?.let {
//                        followingAdapter.differ.submitList(it)
//                        val totalPage = Constants.QUERY_PAGE_SIZE + 2
//                        isLastPage = viewModel.userFollowingPage == totalPage
//                        if (isLastPage) {
//                            rvListFollow.setPadding(0,10,0,0)
//                        }
//                    }
//                }
//                is Resource.Error -> {
//                    resource.message?.let { message ->
//                        showLoading(false)
//                        layoutNoData.visibility = View.VISIBLE
//                        tvFollowNoData.text = getString(R.string.no_internet_title)
//                        tvFollowNoData.text = getString(R.string.no_internet_description)
//                        sweep.isRefreshing = false
//                        Toast.makeText(context, "An error: $message", Toast.LENGTH_LONG).show()
//                    }
//                }
//                is Resource.Loading -> {
//                    showLoading(true)
////                    sweepRefresh.isRefreshing = true
//                }
//            }
//        })
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun prepareData() {
//        val username = (activity as DetailActivity).args
//        val useName = username.search.login
//
//        if (useName != null) {
//            viewModel.followingFetch(useName)
//            layoutNoData.visibility = View.GONE
//        } else {
//            layoutNoData.visibility = View.VISIBLE
//        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

    }

    private fun setupRv() {
        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()

        rvListFollow.apply {
            adapter = followingAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(this@FragmentFollowing.scrollListener)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) pbLoad.visibility = View.VISIBLE
        else pbLoad.visibility = View.GONE
    }
}