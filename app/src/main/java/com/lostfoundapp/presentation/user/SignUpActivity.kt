package com.lostfoundapp.presentation.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lostfoundapp.ConfirmEmailActivity
import com.lostfoundapp.MainActivity
import com.lostfoundapp.R
import com.lostfoundapp.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private var animation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setSupportActionBar(bgHeader)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal)
        rlayout.setAnimation(animation)
        cv.setBackgroundResource(R.drawable.card_background)
        btnSignup.setOnClickListener {
            val email = edtEmailSignup.text.toString().trim()
            val name = edtNameSignup.text.toString().trim()
            val phone = edtPhoneSignup.text.toString().trim()
            val password = edtPasswordSignup.text.toString().trim()
            val confirmPassword = edtConfirmPasswordSignup.text.toString().trim()

            if (email.isEmpty()){
                edtEmailSignup.error = "Email obrigatório"
                edtEmailSignup.requestFocus()
                return@setOnClickListener
            }
            if (name.isEmpty()){
                edtEmailSignup.error = "Nome obrigatório"
                edtEmailSignup.requestFocus()
                return@setOnClickListener
            }
            if (phone.isEmpty()){
                edtPhoneSignup.error = "Telefone obrigatório"
                edtPhoneSignup.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                edtPasswordSignup.error = "Senha obrigatório"
                edtPasswordSignup.requestFocus()
                return@setOnClickListener
            }
            if (confirmPassword.isEmpty()){
                edtConfirmPasswordSignup.error = "Confirmar senha obrigatório"
                edtConfirmPasswordSignup.requestFocus()
                return@setOnClickListener
            }
            if (!confirmPassword.equals(password)){
                edtConfirmPasswordSignup.error = "A senha precisa ser igual"
                edtConfirmPasswordSignup.requestFocus()
                return@setOnClickListener
            }

            val viewModel: SignUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
            viewModel.signUpLiveData.observe(this, Observer {
                val intent = Intent(this@SignUpActivity, ConfirmEmailActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                intent.putExtra("confirmPassword", confirmPassword)
                intent.putExtra("name", name)
                intent.putExtra("phone", phone)
                startActivity(intent)
                finish()
            })

            viewModel.signUp(email, this)
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

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
