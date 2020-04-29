package com.byandev.submission1githubuser

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollower: TypedArray
    private lateinit var dataFollowing: TypedArray
    private lateinit var dataRepository: TypedArray
    private lateinit var dataUserName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataPhoto: TypedArray
    private var userss = arrayListOf<DataSource>()
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lv_list)
        adapter = UserAdapter(this)
        listView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        prepareData()
        addItemToAdapter()
//        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            Toast.makeText(this@MainActivity, userss[position].name, Toast.LENGTH_SHORT).show()
//            val users = DataSource
//            val intent = Intent(this, DetailUserActivity::class.java)
//            intent.putExtra(DetailUserActivity.EXTRA_USER, users)
//        }
    }

    private fun addItemToAdapter() {
        for (position in dataUserName.indices) {
            val users = DataSource(
                dataPhoto.getResourceId(position,-1).toString(),
                dataCompany[position],
                dataFollower.getResourceId(position, -1),
                dataFollowing.getResourceId(position, -1),
                dataLocation[position],
                dataName[position],
                dataRepository.getResourceId(position, -1),
                dataUserName[position]
            )
            userss.add(users)
        }
        adapter.users = userss
    }

    private fun prepareData() {
        dataName = resources.getStringArray(R.array.name)
        dataUserName = resources.getStringArray(R.array.username)
        dataLocation = resources.getStringArray(R.array.location)
        dataPhoto = resources.obtainTypedArray(R.array.avatar)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollower = resources.obtainTypedArray(R.array.followers)
        dataFollowing = resources.obtainTypedArray(R.array.following)
        dataRepository = resources.obtainTypedArray(R.array.repository)
    }
}
