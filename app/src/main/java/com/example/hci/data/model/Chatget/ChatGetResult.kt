package com.example.hci.data.model

data class ChatGetResult(
    var id : Int,
    var sender_id : Int,
    var receiver_id : Int,
    var content : String,
    var created_at : String,        //format ex) "2023-06-03 17:05:24"
    var isSent : Int
)