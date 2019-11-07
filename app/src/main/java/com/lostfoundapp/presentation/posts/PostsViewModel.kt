package com.lostfoundapp.presentation.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lostfoundapp.R
import com.lostfoundapp.data.network.ApiService
import com.lostfoundapp.data.model.Post
import com.lostfoundapp.data.response.responsePost.PostBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel : ViewModel() {
    val postsLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    private val STATUS: String = "Aguardando"

    fun getPost(){

        val res = ApiService.service.getPosts(STATUS)

        res.enqueue(object: Callback<PostBodyResponse>{

            override fun onResponse(call: Call<PostBodyResponse>, response: Response<PostBodyResponse>) {
                when {
                    response.isSuccessful -> {
                        val posts: MutableList<Post> = mutableListOf()
                        response.body()?.let { postBodyResponse ->
                            for (result in postBodyResponse.posts){
                                val post = result.getPostModel()

                                posts.add(post)
                            }
                        }

                        postsLiveData.value = posts
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_POST, null)
                    }
                    response.code() == 401 -> viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_401)
                    else -> viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_400_generic)
                }

            }

            override fun onFailure(call: Call<PostBodyResponse>, t: Throwable) {
                viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_500_generic)
            }

        })
    }

    companion object{
        private const val VIEW_FLIPPER_POST = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

}