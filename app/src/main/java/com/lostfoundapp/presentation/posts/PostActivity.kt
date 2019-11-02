package com.lostfoundapp.presentation.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lostfoundapp.R
import com.lostfoundapp.data.model.Post
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)



        val viewModel: PostsViewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)
        viewModel.postsLiveData.observe(this, Observer {
            it?.let{post ->
                with(recyclerPost){
                    layoutManager = LinearLayoutManager(this@PostActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = PostAdapter(post)
                }

            }
        })
        viewModel.getPost()
    }

    fun getPost(): List<Post>{
        return listOf(
                Post("url", "Teste", "02/11/2019"),
                Post("url", "Teste", "02/11/2019"),
                Post("url", "Teste", "02/11/2019"),
                Post("url", "Teste", "02/11/2019"),
                Post("url", "Teste", "02/11/2019")
        )
    }
}
