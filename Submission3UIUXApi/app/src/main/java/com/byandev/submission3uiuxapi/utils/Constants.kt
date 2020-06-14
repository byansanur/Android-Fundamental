package com.byandev.submission3uiuxapi.utils

import com.byandev.submission3uiuxapi.BuildConfig.tokenKeys

class Constants  {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val TOKEN = tokenKeys
        const val SEARCH_DELAY = 1000L
        const val DETAIL_FOLLOW_DELAY = 100L
        var PAGE_SIZE = 1
    }
}