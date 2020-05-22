package com.byandev.submission2uiux.utils

import com.byandev.submission2uiux.BuildConfig.NewsApiKey


class Constants  {
    companion object {
        const val BASE_URL = "https://api.github.com/"
        var TOKEN_KEY = NewsApiKey
        const val SEARCH_TIME_DELAY = 500L
    }
}