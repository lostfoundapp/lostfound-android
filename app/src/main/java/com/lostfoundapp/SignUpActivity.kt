package com.lostfoundapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private var animation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setSupportActionBar(bgHeader)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal)
        rlayout.setAnimation(animation)

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

            RetrofitClient.instance.ConfirmEmail(email)
                .enqueue(object: Callback<DefaultResponse>{
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@SignUpActivity, ConfirmEmailActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        intent.putExtra("confirmPassword", confirmPassword)
                        intent.putExtra("name", name)
                        intent.putExtra("phone", phone)
                        startActivity(intent)
                        finish()
                    }

                })

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
