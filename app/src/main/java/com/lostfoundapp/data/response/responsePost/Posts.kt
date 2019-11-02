package com.lostfoundapp.data.response.responsePost

data class Posts (
    val post_id : String,
    val image : String,
    val userId : String,
    val name : String,
    val description : String,
    val datetime : String,
    val statusId : String,
    val police : Police
)