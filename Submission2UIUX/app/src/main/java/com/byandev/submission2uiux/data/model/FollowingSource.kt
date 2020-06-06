package com.byandev.submission2uiux.data.model


data class FollowingSource(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
) : MutableList<FollowingSource> {
    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: FollowingSource): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<FollowingSource>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): FollowingSource {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: FollowingSource): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<FollowingSource> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: FollowingSource): Int {
        TODO("Not yet implemented")
    }

    override fun add(element: FollowingSource): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(index: Int, element: FollowingSource) {
        TODO("Not yet implemented")
    }

    override fun addAll(index: Int, elements: Collection<FollowingSource>): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection<FollowingSource>): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun listIterator(): MutableListIterator<FollowingSource> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): MutableListIterator<FollowingSource> {
        TODO("Not yet implemented")
    }

    override fun remove(element: FollowingSource): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<FollowingSource>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAt(index: Int): FollowingSource {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<FollowingSource>): Boolean {
        TODO("Not yet implemented")
    }

    override fun set(index: Int, element: FollowingSource): FollowingSource {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<FollowingSource> {
        TODO("Not yet implemented")
    }
}