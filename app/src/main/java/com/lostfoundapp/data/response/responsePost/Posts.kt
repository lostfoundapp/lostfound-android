package com.lostfoundapp.data.response.responsePost

import com.lostfoundapp.data.model.Post

data class Posts (
    val post_id : String,
    val image : String,
    val userId : String,
    val name : String,
    val description : String,
    val datetime : String,
    val statusId : String,
    val police : Police
){
    fun getPostModel() = Post(
            post_id = this.post_id,
            image = this.image,
            description = this.description.toUpperCase(),
            datetime = this.datetime,
            name = this.name,
            localDeEntrega = this.police.name + " "
                    + this.police.address+ " "
                    + this.police.city+ " "
                    + this.police.district
    )
}