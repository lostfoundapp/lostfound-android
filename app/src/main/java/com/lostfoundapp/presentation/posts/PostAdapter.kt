package com.lostfoundapp.presentation.posts

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lostfoundapp.data.model.Post
import kotlinx.android.synthetic.main.item_post.view.*
import android.net.Uri
import android.graphics.Bitmap
import com.lostfoundapp.R
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import android.os.StrictMode
import com.lostfoundapp.storage.SharingImage.Companion.getOutputMediaFile
import com.lostfoundapp.storage.SharingImage.Companion.storeImage


class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): PostsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return  PostsViewHolder(itemView)
    }

    override fun getItemCount() = posts.count()

    override fun onBindViewHolder(viewHolder: PostsViewHolder, position: Int) {
        viewHolder.bindView(posts[position])
    }

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //id do xml item
        private val nameUser = itemView.nameUser
        private val description = itemView.description
        private val datetime = itemView.datetime
        private val addressPolice = itemView.addressPolice
        private val imgPost = itemView.imgPost
        private val share = itemView.share

        fun bindView(post: Post) {
            nameUser.text = post.name
            description.text = post.description
            datetime.text = post.datetime
            addressPolice.text = post.localDeEntrega
            itemView.cv.setBackgroundResource(R.drawable.recycler_background)

            Glide
                    .with(itemView.context)
                    .load(post.image)
                    .centerCrop()
                    .placeholder(R.drawable.load)
                    .into(imgPost)

            share.setOnClickListener {

                Glide.with(itemView.context)
                    .asBitmap()
                    .load(post.image)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            storeImage(resource, itemView.context)
                            val file = getOutputMediaFile(itemView.context)!!
                            val builder = StrictMode.VmPolicy.Builder()
                            StrictMode.setVmPolicy(builder.build())
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.putExtra(Intent.EXTRA_TEXT, "*(" + post.description + ")*" +
                                    "\n\n" + post.localDeEntrega + "\nvia: LostFound App");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                            intent.type = "image/*"
                            itemView.context.startActivity(Intent.createChooser(intent, "Share with..."))
                        }
                    })
            }
        }
    }
}