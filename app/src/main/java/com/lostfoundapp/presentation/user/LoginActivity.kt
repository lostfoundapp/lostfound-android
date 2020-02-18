package com.lostfoundapp.presentation.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lostfoundapp.Main2Activity
import com.lostfoundapp.MainActivity
import com.lostfoundapp.R
import com.lostfoundapp.presentation.posts.PostActivity
import com.lostfoundapp.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var animation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal)
        rlayout.setAnimation(animation)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        cv.setBackgroundResource(R.drawable.card_background)
        tvForgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtemailLogin.text.toString().trim()
            val password = edtPasswordLogin.text.toString().trim()

            if (email.isEmpty()){
                edtemailLogin.error = "Digite seu email"
                edtemailLogin.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                edtPasswordLogin.error = "Digite sua senha"
                edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            val viewModel: UserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
            viewModel.userLiveData.observe(this, Observer {
                    val intent = Intent(this@LoginActivity, Main2Activity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
            })

            viewModel.login(email, password, this)

        }

    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(this@LoginActivity, Main2Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

}
