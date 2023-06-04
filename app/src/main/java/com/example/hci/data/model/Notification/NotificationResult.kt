package com.example.hci.data.model

data class NotificationResult(
    var message : String        //친구요청, 모임초대, 가입요청중 하나라도 있으면 "true", 없으면 "false"
)
