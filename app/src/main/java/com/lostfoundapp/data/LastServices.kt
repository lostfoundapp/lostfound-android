package com.lostfoundapp.data

import com.lostfoundapp.data.response.responsePost.PostBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LastServices {
    @GET("getPosts")
    fun getPosts(
        @Query("status") status: String
    ):Call<PostBodyResponse>
}