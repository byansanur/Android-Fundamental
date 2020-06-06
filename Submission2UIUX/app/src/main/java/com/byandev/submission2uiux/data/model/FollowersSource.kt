package com.byandev.submission2uiux.data.model


data class FollowersSource(
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var type: String? = null
) : List<FollowersSource> {
    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: FollowersSource): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<FollowersSource>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): FollowersSource {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: FollowersSource): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<FollowersSource> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: FollowersSource): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<FollowersSource> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<FollowersSource> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<FollowersSource> {
        TODO("Not yet implemented")
    }
}