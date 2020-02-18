package com.lostfoundapp.presentation.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lostfoundapp.R
import kotlinx.android.synthetic.main.activity_confirm_email.*


class ConfirmEmailActivity : AppCompatActivity() {

    private var animation: Animation? = null
    private lateinit var emailVeri: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_email)
        cv.setBackgroundResource(R.drawable.card_background)
        var email: String? = intent.getStringExtra("email")
        var password: String? = intent.getStringExtra("password")
        var confirmPassword: String? = intent.getStringExtra("confirmPassword")
        var name: String? = intent.getStringExtra("name")
        var phone: String? = intent.getStringExtra("phone")

        setSupportActionBar(bgHeader)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal)
        rlayout.setAnimation(animation)

        val viewModel: ConfirmEmailViewModel = ViewModelProviders.of(this).get(ConfirmEmailViewModel::class.java)
        viewModel.confirmEmailLiveData.observe(this, Observer {
            it?.let{codigo ->
                emailVeri = codigo

            }
        })

        viewModel.confirmEmail(email.toString(), this)



        btnConfirmEmail.setOnClickListener {
            val emailConfirm = edtConfirmEmail.text.toString().trim()
            if (emailConfirm.isEmpty()){
                edtConfirmEmail.error = "Código obrigatório"
                edtConfirmEmail.requestFocus()
                return@setOnClickListener
            }
            if(emailConfirm.equals(emailVeri)){
                registerUser(name, email,password,confirmPassword,phone)
            }
        }

    }

    private fun registerUser(name: String?, email: String?, password: String?, confirmPassword: String?, phone: String?) {
        val viewModel: ConfirmEmailViewModel = ViewModelProviders.of(this).get(ConfirmEmailViewModel::class.java)
        viewModel.registerUserLiveData.observe(this, Observer {
            it?.let{message ->

                if(message.equals("Success")) {
                    Toast.makeText(
                        applicationContext,
                        "Cadastro efetuado com sucesso",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@ConfirmEmailActivity, LoginActivity::class.java))
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

        viewModel.registerUser(name.toString(), email.toString(), password.toString(),
            confirmPassword.toString(), phone.toString())


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
