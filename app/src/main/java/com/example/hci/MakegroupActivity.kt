package com.example.hci

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class MakegroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makegroup)

        val image1: ImageView = findViewById(R.id.back_image)
        image1.setOnClickListener {
            finish()
        }

        val ImageIcon :ImageView = findViewById(R.id.icon_image)
        val titletext :TextView = findViewById(R.id.grouptype_text)
        val categoryIDX = intent.getIntExtra("categoryIDX", 0)
        when(categoryIDX) {
            0 -> {
                ImageIcon.setImageResource(R.drawable.category_health)
                titletext.text = "운동/스포츠"
                }
            1 -> {
                ImageIcon.setImageResource(R.drawable.category_music)
                titletext.text = "음악/악기"
            }
            2 ->{
                ImageIcon.setImageResource(R.drawable.category_travel)
                titletext.text = "여행"
            }
            3 ->{
                ImageIcon.setImageResource(R.drawable.category_study)
                titletext.text = "스터디"
            }
            4 ->{
                ImageIcon.setImageResource(R.drawable.category_book)
                titletext.text = "독서"
            }
            5 ->{
                ImageIcon.setImageResource(R.drawable.category_language)
                titletext.text = "외국어"
            }
            6 ->{
                ImageIcon.setImageResource(R.drawable.category_culcture)
                titletext.text = "문화/공연/축제"
            }
            7 ->{
                ImageIcon.setImageResource(R.drawable.category_game)
                titletext.text = "게임/오락"
            }
            8 ->{
                ImageIcon.setImageResource(R.drawable.category_volunteer)
                titletext.text = "봉사"
            }
            9 ->{
                ImageIcon.setImageResource(R.drawable.category_pet)
                titletext.text = "반려동물"
            }
            10 ->{
                ImageIcon.setImageResource(R.drawable.category_photo)
                titletext.text = "사진/영상"
            }
            11 ->{
                ImageIcon.setImageResource(R.drawable.category_drive)
                titletext.text = "드라이브"
            }
            else ->{
                ImageIcon.setImageResource(R.drawable.category_health)
                titletext.text = "운동/스포츠"
            }
        }
    }
}