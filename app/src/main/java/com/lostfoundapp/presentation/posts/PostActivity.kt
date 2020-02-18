package com.lostfoundapp.presentation.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lostfoundapp.presentation.user.LoginActivity
import com.lostfoundapp.R
import com.lostfoundapp.storage.SharedPrefManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.bgHeader

class PostActivity : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        footer.setOnClickListener {view ->
            SharedPrefManager.getInstance(requireContext()).clear() //Logout
            onStart()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel: PostsViewModel = ViewModelProviders.of(requireActivity()).get(PostsViewModel::class.java)
        viewModel.postsLiveData.observe(this, Observer {
            it?.let{post ->
                with(recyclerPost){
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = PostAdapter(post)
                }

            }
        })

        viewModel.viewFlipperLiveData.observe(this, Observer {
            it.let {viewFlippers ->
                viewFlipper.displayedChild = viewFlippers.first
                viewFlippers.second?.let {errorMessage ->
                    error.text = getString(errorMessage)
                }
            }
        })
        viewModel.getPost()

        return inflater.inflate(R.layout.fragment_post, container, false)

    }


    override fun onStart() {
        super.onStart()
        //SharedPrefManager.getInstance(this).clear() //Logout
        if (!SharedPrefManager.getInstance(requireContext()).isLoggedIn){
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity!!.finish()
        }else{
            var name = SharedPrefManager.getInstance(requireContext()).user.name
            UserApp.text = name
        }
    }
    /*override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()

    }*/
}
