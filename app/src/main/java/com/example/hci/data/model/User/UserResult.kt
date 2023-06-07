package com.example.hci.data.model

data class UserResult(
    var id : Int,
    var login_id : Int,
    var password : Int,
    var name : String,
    var image : String,
    var score : Double,
    var location_id : Int,
    var email : String,
    var scoreCount : Int
)
