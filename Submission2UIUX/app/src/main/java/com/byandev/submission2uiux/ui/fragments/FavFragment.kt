package com.byandev.submission2uiux.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.ui.MainActivity
import com.byandev.submission2uiux.ui.adapter.SearchAdapter
import com.byandev.submission2uiux.ui.viewModel.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fav.*


class FavFragment : Fragment(R.layout.fragment_fav) {


    lateinit var viewModel: SearchViewModel
    lateinit var adapterSearch: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setRvFav()

        toolbar.title = getString(R.string.favorite)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { findNavController().navigate(R.id.action_favFragment_to_fragmentSearch) }

        adapterSearch.setOnItemClickListener {
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
                val user = adapterSearch.differ.currentList[position]
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

        viewModel.getSavedUser().observe(viewLifecycleOwner, Observer { user ->
            adapterSearch.differ.submitList(user)
        })

    }

    private fun setRvFav() {
        adapterSearch = SearchAdapter()
        rvFav.apply {
            adapter = adapterSearch
            layoutManager = LinearLayoutManager(context)
        }
    }


}