package com.lostfoundapp

import com.lostfoundapp.data.network.ApiUser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{

    private val AUTH = "Bearer "
    public  const val BASE_URL = "https://us-central1-lostandfoundapps.cloudfunctions.net/api/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val  original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val  request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: ApiUser by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiUser::class.java)
    }
}