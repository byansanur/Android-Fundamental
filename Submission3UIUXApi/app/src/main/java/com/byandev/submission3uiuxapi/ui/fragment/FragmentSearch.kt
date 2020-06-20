package com.byandev.submission3uiuxapi.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byandev.submission3uiuxapi.R
import com.byandev.submission3uiuxapi.ui.HomeActivity
import com.byandev.submission3uiuxapi.ui.adapter.SearchAdapter
import com.byandev.submission3uiuxapi.ui.viewModel.home.HomeViewModel
import com.byandev.submission3uiuxapi.utils.Constants.Companion.PAGE_SIZE
import com.byandev.submission3uiuxapi.utils.Constants.Companion.SEARCH_DELAY
import com.byandev.submission3uiuxapi.utils.Resource
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentSearch : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: SearchAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menus, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.menu_fav -> {
//                Toast.makeText(context, "asdsa", Toast.LENGTH_SHORT).show()
//            }
//            R.id.menu_setting -> {
//                Toast.makeText(context, "gfgf", Toast.LENGTH_SHORT).show()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel
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



        layoutNoData.visibility = View.VISIBLE


        textChangeListener()

        sweepRefresh.setOnRefreshListener {
            textChangeListener()
        }

        viewModel.searchUsers.observe(viewLifecycleOwner, Observer { it ->
            when(it) {
                is Resource.Success -> {

                    layoutNoData.visibility = View.GONE
                    sweepRefresh.isRefreshing = false
                    it.data?.let {
                        if (it.items.isNullOrEmpty()) {
                            layoutNoData.visibility = View.VISIBLE
                        } else {
                            searchAdapter.differ.submitList(it.items.toList())
                            val totalPage = it.total_count!! / PAGE_SIZE + 2
                            isLastPage = viewModel.searchUsersPage == totalPage
                            if (isLastPage) {
                                rvListSearch.setPadding(0,10,0,0)
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->

                        layoutNoData.visibility = View.VISIBLE
                        sweepRefresh.isRefreshing = false
                        Toast.makeText(activity, "An error: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {

                    sweepRefresh.isRefreshing = true
                }
            }
        })
    }


    private fun textChangeListener() {
        sweepRefresh.isRefreshing = false
        var job: Job? = null
        etSearch.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_DELAY)
                editable.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.fetchSearch(editable.toString())
                        searchAdapter.notifyDataSetChanged()
                        layoutNoData.visibility = View.GONE
                    }
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
            val isTotalMoreThanVisible = totalItemCount >= PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.fetchSearch(etSearch.text.toString())
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
        rvListSearch.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(context, 2)
            addOnScrollListener(this@FragmentSearch.scrollListener)
        }
    }


}