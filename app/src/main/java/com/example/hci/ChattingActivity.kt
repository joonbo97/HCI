package com.example.hci

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class ChattingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        val backbtn :ImageView = findViewById(R.id.back_image)
        backbtn.setOnClickListener {
            finish()
        }

        val message :EditText = findViewById(R.id.edit_message)

        val titletext :TextView = findViewById(R.id.username)
        titletext.text = intent.getStringExtra("friendname") + "님 과의 대화"

    }
}