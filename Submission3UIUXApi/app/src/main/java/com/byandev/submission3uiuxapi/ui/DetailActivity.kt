package com.byandev.submission3uiuxapi.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUserViewModel
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUserViewModelFactory
import com.byandev.submission3uiuxapi.R
import com.byandev.submission3uiuxapi.db.dao.UserFavDao
import com.byandev.submission3uiuxapi.db.repository.AppRepository
import com.byandev.submission3uiuxapi.ui.adapter.FollowersAdapter
import com.byandev.submission3uiuxapi.ui.adapter.FollowingAdapter
import com.byandev.submission3uiuxapi.ui.viewModel.FaaViewModelFactory
import com.byandev.submission3uiuxapi.ui.viewModel.FavViewModel
import com.byandev.submission3uiuxapi.ui.viewModel.followFollow.FollowersViewModel
import com.byandev.submission3uiuxapi.ui.viewModel.followFollow.FollowersViewModelFactory
import com.byandev.submission3uiuxapi.ui.viewModel.followFollow.FollowingViewModel
import com.byandev.submission3uiuxapi.ui.viewModel.followFollow.FollowingViewModelFactory
import com.byandev.submission3uiuxapi.utils.Constants.Companion.DETAIL_FOLLOW_DELAY
import com.byandev.submission3uiuxapi.utils.Constants.Companion.PAGE_SIZE
import com.byandev.submission3uiuxapi.utils.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.toolbar
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {


    lateinit var viewModel: DetailUserViewModel
    lateinit var viewModelFollower: FollowersViewModel
    lateinit var viewModelFollowing: FollowingViewModel
    private lateinit var adapterFollowers: FollowersAdapter
    private lateinit var adapterFollowing: FollowingAdapter

    private lateinit var favViewModel: FavViewModel
    private val args:DetailActivityArgs by navArgs()

    private lateinit var itemFav: MenuItem

    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detailRepository = AppRepository(UserFavDao(this))
        val detailUserViewModelProviderFactory =
            DetailUserViewModelFactory(
                application,
                detailRepository
            )
        viewModel = ViewModelProvider(this, detailUserViewModelProviderFactory)
            .get(DetailUserViewModel::class.java)

        // for followers following
        val followerFactory = FollowersViewModelFactory(application, detailRepository)
        viewModelFollower = ViewModelProvider(this, followerFactory).get(FollowersViewModel::class.java)

        val followingFactory = FollowingViewModelFactory(application, detailRepository)
        viewModelFollowing = ViewModelProvider(this, followingFactory).get(FollowingViewModel::class.java)

        // for favorite
        val favFactory = FaaViewModelFactory(application, detailRepository)
        favViewModel = ViewModelProvider(this, favFactory).get(FavViewModel::class.java)

        viewModelFollower.userFollowers.observe(this, Observer { it ->
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        if (it.isNullOrEmpty()) {
                            imgNoDataFollowers.visibility = View.VISIBLE
                        } else {
                            adapterFollowers.differ.submitList(it.toList())
                            val totalPage = PAGE_SIZE+2
                            isLastPage = viewModelFollower.pagination == totalPage
                            if (isLastPage) {
                                rvFollowers.setPadding(0,10,0,0)
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    it.message.let {
                        imgNoDataFollowers.visibility = View.VISIBLE
                        Toast.makeText(this, "An error $it", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        })

        viewModelFollowing.userFollowing.observe(this, Observer { it ->
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        if (it.isNullOrEmpty()) {
                            imgNoDataFollowing.visibility = View.VISIBLE
                        } else {
                            adapterFollowing.differ.submitList(it.toList())
                            val totalPage = PAGE_SIZE+2
                            isLastPage = viewModelFollowing.pagination == totalPage
                            if (isLastPage) {
                                rvFollowers.setPadding(0,10,0,0)
                            }
                        }
                    }

                }
                is Resource.Error -> {
                    it.message.let {
                        imgNoDataFollowing.visibility = View.VISIBLE
                        Toast.makeText(this, "An error $it", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        })

        setRvFollowers()
        setRvFollowing()
        prepareData()

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.title = null

//        adapterFollowers.setOnItemClickListener {
//            val bundle = Bundle().apply {
//                putSerializable("search", it)
//            }
//            findNavController().navigate(
//                R.id.action_fragmentSearch_to_detailActivity,
//                bundle
//            )
//        }

        setupUi()


    }

    private fun prepareData() {
        val args = args.search
        val ag = args.login.toString()

        val job: Job? = null
        if (ag.isNotEmpty()) {
            job?.cancel()
            MainScope().launch {
                delay(DETAIL_FOLLOW_DELAY)
                viewModel.detailUserFetch(ag)
                viewModelFollower.followersFetch(ag)
                viewModelFollowing.followingFetch(ag)
            }

        }
    }

    private fun setRvFollowing() {
        adapterFollowing = FollowingAdapter()
        adapterFollowing.notifyDataSetChanged()

        rvFollowing.apply {
            adapter = adapterFollowing
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private fun setRvFollowers() {
        adapterFollowers = FollowersAdapter()
        adapterFollowers.notifyDataSetChanged()

        rvFollowers.apply {
            adapter = adapterFollowers
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
//            addOnScrollListener(this@DetailActivity.scrollFollowers)
        }

    }

    private val scrollFollowers = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
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
                val args = args.search
                viewModelFollower.followersFetch(args.login.toString())
                isScrolling = false
            }
        }
    }

    private fun setupUi() {


        viewModel.detailUsers.observe(this, Observer { it ->
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        if (it.equals(null)) {
                            tvFullName.visibility = View.GONE
                            tvUsername.visibility = View.GONE
                            tvBio.visibility = View.GONE
                            tvCompany.visibility = View.GONE
                            tvLocation.visibility = View.GONE
                            tvBlog.visibility = View.GONE
                            tvCountFollowing.visibility = View.GONE
                            tvCountFollowers.visibility = View.GONE

                        } else {
                            val strImg = it.avatar_url
                            Glide.with(this)
                                .load(strImg)
                                .centerCrop()
                                .into(imgUser)
                            tvFullName.text = it.name
                            tvUsername.text = it.login
                            tvCompany.text = it.company
                            tvLocation.text = it.location
                            tvBlog.text = it.blog
                            tvCountFollowers.text = it.followers.toString() + " Followers"
                            tvCountFollowing.text = it.following.toString() + " Following"

                        }
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(this, "An error: $message", Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fav, menu)
        itemFav = menu?.findItem(R.id.men_fav)!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_fav) {
            if (isFav) {
                itemFav.icon = getDrawable(R.drawable.ic_baseline_favorite_border_24)
            } else {
                itemFav.icon = getDrawable(R.drawable.ic_baseline_favorite_24)
            }
//            val args = args.search
//            favViewModel.saveFavorite(args)
//            Toast.makeText(this, "fav", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//
//        return super.onPrepareOptionsMenu(menu)
//    }

}