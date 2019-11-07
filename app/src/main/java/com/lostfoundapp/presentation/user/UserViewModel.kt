package com.lostfoundapp.presentation.user

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lostfoundapp.data.response.responseUser.LoginResponse
import com.lostfoundapp.data.model.user.User
import com.lostfoundapp.data.network.ApiService
import com.lostfoundapp.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    val userLiveData: MutableLiveData<User> = MutableLiveData()

    fun login(email: String, password:  String, context: Context){
        val res = ApiService.serviceUser.userLogin(email, password)

        res.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("erros",  t.message)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (!response.body()?.message.equals("Logado")){
                }else{
                    SharedPrefManager.getInstance(context).saveUser(response.body()?.users!!)
                    userLiveData.value = response.body()?.users
                }
            }
        })
    }
}