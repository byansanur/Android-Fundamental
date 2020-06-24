package com.byandev.submission2uiux.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.ui.MainActivity
import com.byandev.submission2uiux.ui.adapter.FavAdapter
import com.byandev.submission2uiux.ui.viewModel.search.SearchViewModel
import com.byandev.submission2uiux.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fav.*
import kotlinx.android.synthetic.main.rv_fav_include.*


class FavFragment : Fragment(R.layout.fragment_fav) {


    lateinit var viewModel: SearchViewModel
    lateinit var adapterFav: FavAdapter

    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setRvFav()

        toolbar.title = getString(R.string.favorite)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { findNavController().navigate(R.id.action_favFragment_to_fragmentSearch) }


        adapterFav.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("search", it)
            }
            findNavController().navigate(
                R.id.action_favFragment_to_detailActivity,
                bundle
            )
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val user = adapterFav.differ.currentList[position]
                viewModel.deleteUser(user)
                Snackbar.make(view, "User has deleted successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveUser(user)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvFav)
        }

        imgNoData.visibility = View.VISIBLE
        pbLoad.visibility = View.VISIBLE

        viewModel.getSavedUser().observe(viewLifecycleOwner, Observer {
            imgNoData.visibility = View.GONE
            pbLoad.visibility = View.GONE
            if (it.isNullOrEmpty()) {
                imgNoData.visibility = View.VISIBLE
                pbLoad.visibility = View.GONE
            } else {
                adapterFav.differ.submitList(it.toList())
                if (isLastPage) {
                    rvFav.setPadding(0,10,0,0)
                }
            }

        })


    }

    private fun setRvFav() {
        adapterFav = FavAdapter()
        adapterFav.notifyDataSetChanged()

        rvFav.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterFav
            addOnScrollListener(this@FavFragment.onScrollListener)
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            pbLoad.visibility = View.GONE
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firsVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && ! isLastPage
            val isAtLastItem = firsVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firsVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getSavedUser()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
            pbLoad.visibility = View.GONE
        }
    }


}