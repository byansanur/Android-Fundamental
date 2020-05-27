package com.byandev.submission2uiux.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.byandev.submission2uiux.data.model.Item
import com.byandev.submission2uiux.network.RetrofitInstance
import com.byandev.submission2uiux.ui.adapter.SearchAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragmentViewModel (
    app: Application
) : AndroidViewModel(app) {

    val TAG = "viewModel search"

    val searchItem = MutableLiveData<ArrayList<Item>>()
    val adapter = SearchAdapter()

    fun setSearch(query: String) {
        val searchItems = ArrayList<Item>()

        val client = RetrofitInstance.api
        client.searchUsers(query).enqueue(object : Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "err0 : ${t.message}")
            }

            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        searchItem.postValue(searchItems)
                        adapter.notifyDataSetChanged()
                        Log.e(TAG, "succ : ${response.message()}")
                    } else {
                        Log.e(TAG, "err1 : ${response.message()}")
                    }
                } else Log.e(TAG, "err2 : ${response.message()}")
            }

        })
    }

    fun getSearch(): LiveData<ArrayList<Item>> {
        return searchItem
    }



}

