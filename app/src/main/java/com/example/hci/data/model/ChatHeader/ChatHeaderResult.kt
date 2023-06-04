package com.example.hci.data.model

data class ChatHeaderResult(
    var id : Int,
    var my_id : Int,
    var your_id : Int,
    var last_time : String,         //format example->"2023-06-03 17:39:59"
    var last_message : String,
    var isRead : Int        //0->읽지 않은 메시지 존재, 1->다 읽은 상태
)
