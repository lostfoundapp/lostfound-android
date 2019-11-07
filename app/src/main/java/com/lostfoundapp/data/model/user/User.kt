package com.lostfoundapp.data.model.user

data class User (
    val userId: String,
    val email: String,
    val name: String,
    val phone: String,
    val token: String
)