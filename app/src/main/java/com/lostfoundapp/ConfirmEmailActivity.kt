package com.lostfoundapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_confirm_email.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ConfirmEmailActivity : AppCompatActivity() {

    private var animation: Animation? = null
    private lateinit var emailVeri: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_email)

        var email: String? = intent.getStringExtra("email")
        var password: String? = intent.getStringExtra("password")
        var confirmPassword: String? = intent.getStringExtra("confirmPassword")
        var name: String? = intent.getStringExtra("name")
        var phone: String? = intent.getStringExtra("phone")

        setSupportActionBar(bgHeader)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal)
        rlayout.setAnimation(animation)

        RetrofitClient.instance.GetEmailVerification(email.toString().trim())
            .enqueue(object: Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    Toast.makeText(applicationContext, response.body()?.code, Toast.LENGTH_LONG).show()
                    emailVeri = response.body()?.code.toString().trim()
                }

            })



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
        RetrofitClient.instance.RegisterUser(name.toString(), email.toString(), password.toString(),
                                            confirmPassword.toString(), phone.toString())
                            .enqueue(object: Callback<DefaultResponse> {
                                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                                }

                                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                    //println("Cadastro", response.body()?.message)
                                    Log.d("Cadastro", response.toString())
                                    if(response.body()?.message.toString().equals("Success")) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Cadastro efetuado com sucesso",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        startActivity(Intent(this@ConfirmEmailActivity, LoginActivity::class.java))
                                        finish()
                                    }
                                }

                            })
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
