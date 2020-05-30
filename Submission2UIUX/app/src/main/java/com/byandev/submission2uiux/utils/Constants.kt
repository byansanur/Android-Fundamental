package com.byandev.submission2uiux.utils

import com.byandev.submission2uiux.BuildConfig.TokenKey


class Constants  {
    companion object {
        const val BASE_URL = "https://api.github.com/"
        var TOKEN_KEY = TokenKey
        const val SEARCH_TIME_DELAY = 1000L
        const val QUERY_PAGE_SIZE = 1
    }
}