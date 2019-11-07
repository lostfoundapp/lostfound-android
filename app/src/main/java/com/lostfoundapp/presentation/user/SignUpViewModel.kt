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

class SignUpViewModel : ViewModel() {
    val signUpLiveData: MutableLiveData<DefaultResponse> = MutableLiveData()

    fun signUp(email: String, context: Context){
        val res = ApiService.serviceUser.ConfirmEmail(email)

        res.enqueue(object: Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                signUpLiveData.value = response.body()
            }

        })
    }
}