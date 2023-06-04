package com.example.hci.data.model

data class AuthSendEmailResult(
    var message : String        //인증번호 전송 성공->"인증번호를 보냈습니다.", 실패시->error msg
)
