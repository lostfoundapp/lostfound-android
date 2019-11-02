package com.lostfoundapp.presentation.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lostfoundapp.R
import com.lostfoundapp.data.model.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): PostsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return  PostsViewHolder(itemView)
    }

    override fun getItemCount() = posts.count()

    override fun onBindViewHolder(viewHolder: PostsViewHolder, position: Int) {
        viewHolder.bindView(posts[position])
    }

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //id do xml item
        private val teste = itemView.teste
        fun bindView(post: Post){
            teste.text = post.description + " - " + post.datetime + " - " + post.image
        }
    }
}