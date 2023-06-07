package com.example.hci

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MyfriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfriend)

        val image1: ImageView = findViewById(R.id.back_image)
        image1.setOnClickListener {
            finish()
        }

        val addfriendbtn :ImageView = findViewById(R.id.addfriend_img)

        //친구추가 버튼 클릭
        addfriendbtn.setOnClickListener {
            val dialog = AddFriendDialog(this)
            dialog.showDialog()
        }

    }
}