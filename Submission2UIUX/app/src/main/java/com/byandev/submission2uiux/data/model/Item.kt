package com.byandev.submission2uiux.data.model

import java.io.Serializable

data class Item(
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var type: String? = null
) : Serializable