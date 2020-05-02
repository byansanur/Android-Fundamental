package com.byandev.fundametalandroid8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<DataHero>()
    private var title = "Mode List"
    private var mode: Int = 0
    companion object {
        private const val STATE_TITLE = "state_string"
        private const val STATE_LIST = "state_list"
        private const val STATE_MODE = "state_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_heroes.setHasFixedSize(true)
        setActionBarTitle(title)

        if (savedInstanceState == null) {
            setActionBarTitle(title)
            list.addAll(getListHeroes())
            showRecyclerList()
            mode = R.id.action_list
        } else {
            title = savedInstanceState.getString(STATE_TITLE).toString()
            val stateList = savedInstanceState.getParcelableArrayList<DataHero>(STATE_LIST)
            val stateMode = savedInstanceState.getInt(STATE_MODE)
            setActionBarTitle(title)
            if (stateList != null) {
                list.addAll(stateList)
            }
            setMode(stateMode)
        }

        list.addAll(getListHeroes())
        showRecyclerList()
    }

    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_TITLE, title)
        outState.putParcelableArrayList(STATE_LIST, list)
        outState.putInt(STATE_MODE, mode)
    }

    fun getListHeroes(): ArrayList<DataHero> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val listHero = ArrayList<DataHero>()
        for (position in dataName.indices) {
            val hero = DataHero(
                dataName[position],
                dataDescription[position],
                dataPhoto[position]
            )
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        rv_heroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListAdapterHero(list)
        rv_heroes.adapter = listHeroAdapter
    }

    private fun showRecyclerGrid() {
        rv_heroes.layoutManager = GridLayoutManager(this, 2)
        val gridHeroAdapter = GridHeroAdapter(list)
        rv_heroes.adapter = gridHeroAdapter
    }

    private fun showRecyclerCardView() {
        rv_heroes.layoutManager = LinearLayoutManager(this)
        val cardViewHeroAdapter = CardViewHeroAdapter(list)
        rv_heroes.adapter = cardViewHeroAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }
    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.action_list -> {
                title = "Mode List"
                showRecyclerList()
            }
            R.id.action_grid -> {
                title = "Mode Grid"
                showRecyclerGrid()
            }
            R.id.action_cardview -> {
                title = "Mode Card View"
                showRecyclerCardView()
            }
        }
        mode = selectedMode
        setActionBarTitle(title)
    }
}
