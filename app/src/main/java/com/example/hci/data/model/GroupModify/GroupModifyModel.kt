package com.example.hci.data.model

data class GroupModifyModel(
    var group_id : Int,
    var uid : Int,
    var area : String,
    var date : String,
    var start_time : String,
    var end_time : String,
    var name : String,
    var description : String,
    var capacity : Int
)
