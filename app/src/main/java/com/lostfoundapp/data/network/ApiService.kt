package com.lostfoundapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    const val BASE_URL = "https://us-central1-lostandfoundapps.cloudfunctions.net/api/"
    private fun initRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service = initRetrofit().create(ApiPost::class.java)!!
    val serviceUser = initRetrofit().create(ApiUser::class.java)!!
}