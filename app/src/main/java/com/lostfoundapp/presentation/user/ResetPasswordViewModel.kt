package com.lostfoundapp.presentation.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lostfoundapp.data.network.ApiService
import com.lostfoundapp.data.response.responseUser.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordViewModel: ViewModel() {
    val resetPasswordUserLiveData: MutableLiveData<String> = MutableLiveData()

    fun validReset(email: String){
        val res = ApiService.serviceUser.PostForgot(email)

        res.enqueue(object: Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                resetPasswordUserLiveData.value = t.message.toString()
            }

            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                resetPasswordUserLiveData.value = response.body()?.message.toString()
            }

        })
    }

}