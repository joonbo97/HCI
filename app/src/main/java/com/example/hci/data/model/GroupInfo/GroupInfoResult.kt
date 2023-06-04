package com.example.hci.data.model

data class GroupInfoResult(
    var name : String,
    var score : Double,
    var creator : String,
    var headcount : Int,
    var capacity : Int,
    var description : String,
    var date : String,
    var start_time : String,
    var end_time : String,
    var area : String
)