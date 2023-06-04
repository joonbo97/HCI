package com.example.hci.data.model

data class GroupCreateModel(
    var uid : Int,
    var area : String,
    var date : String,
    var start_time : String,
    var end_time : String,
    var name : String,
    var description : String,
    var location : Int,
    var capacity : Int,
    var category : Int
)
