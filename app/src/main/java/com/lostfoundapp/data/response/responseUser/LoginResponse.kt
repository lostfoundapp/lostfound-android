package com.lostfoundapp.data.response.responseUser

import com.lostfoundapp.data.model.user.User

data class LoginResponse (val message: String, val error: String, val  users: User)