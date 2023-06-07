package com.example.hci.Alarm

data class GroupInvite(
    var sender_uid :Int,
    var sender_name :String,
    var sender_email :String,
    var group_id :Int,
    var group_name :String,
    var group_location :String,
    var group_date :String,
    var group_start :String,
    var group_end :String,
    var group_headcount :Int,
    var group_capacity :Int,
    var group_score :Double
)
