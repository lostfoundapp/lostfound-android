package com.lostfoundapp.data.network

import com.lostfoundapp.data.response.responseUser.DefaultResponse
import com.lostfoundapp.data.response.responseUser.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiUser {
    @FormUrlEncoded
    @POST("verificationEmail")
    fun ConfirmEmail(
        @Field("email") email: String
    ): Call<DefaultResponse>

    @GET("verificationCode/{email}")
    fun GetEmailVerification(
        @Path("email") email: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("signup")
    fun RegisterUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
        @Field("phone") phone: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("forgotPassword")
    fun PostForgot(
        @Field("email") email: String
    ): Call<DefaultResponse>

}