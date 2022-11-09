package com.example.fitapet.retrofit.dto


//response
data class FriendDTO(
    val isSuccess:String,
    val code:Int,
    val message:String,
    val result:List<Friend>
)

data class Friend(
    val example:String
)
