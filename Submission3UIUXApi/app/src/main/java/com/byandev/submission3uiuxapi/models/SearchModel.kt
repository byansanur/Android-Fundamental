package com.byandev.submission3uiuxapi.models



data class SearchModel(
    val incomplete_results: Boolean?,
    val items: List<Item?>,
    val total_count: Int?
)