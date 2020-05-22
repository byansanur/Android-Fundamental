package com.byandev.submission2uiux.data.model

data class SearchModel(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)