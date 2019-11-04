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
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.*
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.text.SimpleDateFormat
import java.util.*
import android.os.StrictMode





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
        private val nameUser = itemView.nameUser
        private val description = itemView.description
        private val datetime = itemView.datetime
        private val addressPolice = itemView.addressPolice
        private val imgPost = itemView.imgPost
        private val share = itemView.share

        fun bindView(post: Post){
            nameUser.text = post.name
            description.text = post.description
            datetime.text = post.datetime
            addressPolice.text = post.localDeEntrega

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
                            storeImage(resource)
                            val file = getOutputMediaFile()!!
                            val builder = StrictMode.VmPolicy.Builder()
                            StrictMode.setVmPolicy(builder.build())
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.putExtra(Intent.EXTRA_TEXT, "("+post.description+")"+
                                                "\n\n"+post.localDeEntrega+"\nvia: LostFound App");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                            intent.type = "image/*"
                            itemView.context.startActivity(Intent.createChooser(intent, "Share with..."))
                        }
                    })
            }
        }
        private fun storeImage(image: Bitmap) {
            val pictureFile = getOutputMediaFile()
            if (pictureFile == null) {
                Log.d(
                    "caminho",
                    "Error creating media file, check storage permissions: "
                )
                return
            }
            try {
                val fos = FileOutputStream(pictureFile)
                image.compress(Bitmap.CompressFormat.PNG, 90, fos)
                fos.close()
            } catch (e: FileNotFoundException) {
                Log.d("caminho", "File not found: " + e.message)
            } catch (e: IOException) {
                Log.d("caminho", "Error accessing file: " + e.message)
            }

        }
        private fun getOutputMediaFile():File? {
            val mediaStorageDir = File(
                (Environment.getExternalStorageDirectory()).toString()
                        + "/Android/data/"
                        + itemView.context.getPackageName()
                        + "/Files"
            )
            if (!mediaStorageDir.exists())
            {
                if (!mediaStorageDir.mkdirs())
                {
                    return null
                }
            }
            val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
            val mediaFile:File
            val mImageName = "MI_$timeStamp.jpg"
            mediaFile = File(mediaStorageDir.getPath() + File.separator + mImageName)
            return mediaFile
        }
    }
}