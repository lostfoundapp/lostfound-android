package com.lostfoundapp.data.response.responsePost

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class PostBodyResponse (
    val posts : List<Posts>
)