package com.lostfoundapp.presentation.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lostfoundapp.R
import com.lostfoundapp.RetrofitClient
import com.lostfoundapp.data.ApiService
import com.lostfoundapp.data.model.Post
import com.lostfoundapp.data.response.responsePost.PostBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class PostsViewModel : ViewModel() {
    val postsLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    private val STATUS: String = "Aguardando"

    fun getPost(){

        val res = ApiService.service.getPosts(STATUS)
        Log.d("POST" ,res.toString())
            res.enqueue(object: Callback<PostBodyResponse>{

                override fun onResponse(call: Call<PostBodyResponse>, response: Response<PostBodyResponse>) {
                   if (response.isSuccessful){
                       val posts: MutableList<Post> = mutableListOf()
                       response.body()?.let { postBodyResponse ->
                           for (result in postBodyResponse.posts){
                               val post = Post(
                                   image = result.image,
                                   description = result.description,
                                   datetime = result.datetime
                               )
                               posts.add(post)
                           }
                       }

                       postsLiveData.value = posts
                   }
                }

                override fun onFailure(call: Call<PostBodyResponse>, t: Throwable) {
                    Log.d("POSTS", t.message)
                }

            })
    }

}