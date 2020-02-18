package com.lostfoundapp.presentation.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lostfoundapp.R
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    private var animation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        setSupportActionBar(bgHeader)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal)
        rlayout.setAnimation(animation)
        cv.setBackgroundResource(R.drawable.card_background)
        btnResetEmail.setOnClickListener {
            val emailConfirm = edtResetEmail.text.toString().trim()
            if (emailConfirm.isEmpty()){
                edtResetEmail.error = "Email obrigatório"
                edtResetEmail.requestFocus()
                return@setOnClickListener
            }

            sendReset(emailConfirm)
        }
    }

    private fun sendReset(email: String) {
        val viewModel: ResetPasswordViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java)
        viewModel.resetPasswordUserLiveData.observe(this, Observer {
            it?.let{message ->

                if(message.equals("Email sent")) {
                    Toast.makeText(
                        applicationContext,
                        message,
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }else {
                    Toast.makeText(
                        applicationContext,
                        message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        viewModel.validReset(email)
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
