package com.byandev.submission1githubuser

import android.content.res.TypedArray
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollower: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataUserName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataPhoto: TypedArray
    private var userArray = arrayListOf<DataSource>()
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lv_list)
        adapter = UserAdapter(this)
        listView.adapter = adapter
        toolbar.title = resources.getString(R.string.app_name)
    }

    override fun onStart() {
        super.onStart()
        prepareData()
        addItemToAdapter()
    }

    private fun addItemToAdapter() {
        for (i in dataUserName.indices) {
            val usersData = DataSource(
                dataPhoto.getResourceId(i,-1).toString(),
                dataCompany[i],
                dataFollower[i].toInt(),
                dataFollowing[i].toInt(),
                dataLocation[i],
                dataName[i],
                dataRepository[i].toInt(),
                dataUserName[i]
            )
            userArray.add(usersData)
        }
        adapter.users = userArray
    }

    private fun prepareData() {
        dataName = resources.getStringArray(R.array.name)
        dataUserName = resources.getStringArray(R.array.username)
        dataLocation = resources.getStringArray(R.array.location)
        dataPhoto = resources.obtainTypedArray(R.array.avatar)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollower = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataRepository = resources.getStringArray(R.array.repository)
    }
}
