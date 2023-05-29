package com.example.hci

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class SelectCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        val categoryIDX = intent.getIntExtra("categoryIDX", 0)
        val temptext : TextView = this.findViewById(R.id.category_text)

        ChangeTitle(categoryIDX, temptext)

        val image1: ImageView = findViewById(R.id.back_image)

        image1.setOnClickListener {
            finish()
        }
    }
}

fun ChangeTitle(categoryIDX: Int, TempText: TextView)//타이틀을 바꾸는 함수
{
    when(categoryIDX)
    {
        0 -> TempText.text = "운동/스포츠"
        1 -> TempText.text = "음악/악기"
        2 -> TempText.text = "여행"
        3 -> TempText.text = "스터디"
        4 -> TempText.text = "독서"
        5 -> TempText.text = "외국어"
        6 -> TempText.text = "문화/공연/축제"
        7 -> TempText.text = "게임/오락"
        8 -> TempText.text = "봉사"
        9 -> TempText.text = "반려동물"
        10-> TempText.text = "사진/영상"
        11-> TempText.text = "드라이브"
    }
}