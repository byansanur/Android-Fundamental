package com.byandev.submission2uiux.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.ui.fragments.FragmentFollowers
import com.byandev.submission2uiux.ui.fragments.FragmentFollowing

class ViewPagerAdapter (private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_TITLE = intArrayOf(
        R.string.tab_followers,
        R.string.tab_following
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FragmentFollowers()
            1 -> fragment = FragmentFollowing()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLE[position])
    }

    override fun getCount(): Int = 2
}