package com.lostfoundapp.data.network

import com.lostfoundapp.data.response.responsePost.PostBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiPost {
    @GET("getPosts")
    fun getPosts(
        @Query("status") status: String
    ):Call<PostBodyResponse>
}