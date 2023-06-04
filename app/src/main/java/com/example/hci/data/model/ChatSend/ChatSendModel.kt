package com.example.hci.data.model

data class ChatSendModel(
    var uid : Int,
    var receiver : Int,         //receiver_uid
    var content : String
)
