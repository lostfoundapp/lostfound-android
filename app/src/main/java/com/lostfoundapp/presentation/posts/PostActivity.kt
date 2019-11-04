package com.lostfoundapp.presentation.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lostfoundapp.LoginActivity
import com.lostfoundapp.R
import com.lostfoundapp.data.model.Post
import com.lostfoundapp.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_confirm_email.*
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post.bgHeader

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        setSupportActionBar(bgHeader)
        //supportActionBar!!.setTitle("Documentos perdidos")
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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

    override fun onStart() {
        super.onStart()

        if (!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(this@PostActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }else{
            var name = SharedPrefManager.getInstance(applicationContext).user.name
            UserApp.text = name
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()

    }
}
