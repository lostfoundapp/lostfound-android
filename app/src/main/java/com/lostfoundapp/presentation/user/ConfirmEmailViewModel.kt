package com.lostfoundapp.presentation.user

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lostfoundapp.data.network.ApiService
import com.lostfoundapp.data.response.responseUser.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmEmailViewModel : ViewModel() {
    val confirmEmailLiveData: MutableLiveData<String> = MutableLiveData()
    val registerUserLiveData: MutableLiveData<String> = MutableLiveData()

    fun confirmEmail(email: String, context: Context){
        val res = ApiService.serviceUser.GetEmailVerification(email)

        res.enqueue(object: Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                //Toast.makeText(context, response.body()?.code.toString(), Toast.LENGTH_LONG).show()
                confirmEmailLiveData.value = response.body()?.code.toString()
            }

        })

    }

    fun registerUser(name: String?, email: String?, password: String?, confirmPassword: String?, phone: String?){
        val res = ApiService.serviceUser.RegisterUser(name.toString(), email.toString(), password.toString(),
            confirmPassword.toString(), phone.toString())

        res.enqueue(object: Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                registerUserLiveData.value = t.message.toString()
            }

            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                //Toast.makeText(context, response.body()?.code.toString(), Toast.LENGTH_LONG).show()
                registerUserLiveData.value = response.body()?.message.toString()
            }

        })
    }
}