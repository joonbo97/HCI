package com.example.hci.ui.MyProfile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.hci.R

class EditprofileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        val image1: ImageView = findViewById(R.id.back_image4)
        image1.setOnClickListener {
            finish()
        }

        val done :ImageView = findViewById(R.id.edit_btn2)
        done.setOnClickListener{
            //TODO : 변경완료버튼 눌렀을 때의 Action 수행
            //TODO : 완료 누르면 이전 Activity로 돌아감
            //TODO : 변경 되는 사진은 myphotoimage1 myphotoimage2 는 카메라버튼
        }

        val camera :ImageView = findViewById(R.id.myphoto_image2)
        camera.setOnClickListener {
            //TODO : 카메라버튼(프사변경)수행
            
        }
    }
}