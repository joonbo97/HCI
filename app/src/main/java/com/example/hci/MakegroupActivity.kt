package com.example.hci

import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.hci.data.model.GroupCreateModel

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

        val Edit_Area :EditText = findViewById(R.id.local_edit)
        val Edit_DateYear :EditText = findViewById(R.id.date_year_edit)
        val Edit_DateMonth :EditText = findViewById(R.id.date_month_edit)
        val Edit_DateDay :EditText = findViewById(R.id.date_day_edit)
        val Edit_StartHour :EditText = findViewById(R.id.starttime_hour_edit)
        val Edit_StartMin :EditText = findViewById(R.id.starttime_min_edit)
        val Edit_EndHour :EditText = findViewById(R.id.endtime_hour_edit)
        val Edit_EndMin :EditText = findViewById(R.id.endtime_min_edit)
        val Edit_Name :EditText = findViewById(R.id.groupname_edit)
        val Edit_discription :EditText = findViewById(R.id.groupdiscription_edit)
        val Edit_capacity :EditText = findViewById(R.id.maxpeople_edit)
        val done :ImageView = findViewById(R.id.imageView17)

        fun isempty() :Boolean{ //비었으면 true
            return (Edit_Area.length() == 0) || (Edit_DateYear.length() == 0) || (Edit_DateMonth.length() == 0) || (Edit_DateDay.length() == 0) || (Edit_StartHour.length() == 0) || (Edit_StartMin.length() == 0) ||
                    (Edit_EndHour.length() == 0) || (Edit_EndMin.length() == 0) || (Edit_Name.length() == 0) || (Edit_discription.length() == 0)
        }


        done.setOnClickListener {
            if(isempty())
                Toast.makeText(this, "빈칸이 있습니다. 모두 작성해주세요.", Toast.LENGTH_SHORT).show()
            else
            {
                val area = Edit_Area.text.toString()
                val date_year = Edit_DateYear.text.toString()
                val date_month = Edit_DateMonth.text.toString()
                val date_day = Edit_DateDay.text.toString()
                val start_hour = Edit_StartHour.text.toString()
                val start_min = Edit_StartMin.text.toString()
                val end_hour = Edit_EndHour.text.toString()
                val end_min = Edit_EndMin.text.toString()
                val name = Edit_Name.text.toString()
                val discription = Edit_discription.text.toString()
                val capacity = Edit_capacity.text.toString().toInt()

                val date = "$date_year-$date_month-$date_day"
                val start_time = "$date $start_hour:$start_min:00"
                val end_time = "$date $end_hour:$end_min:00"

                createGroup(GroupCreateModel(MainActivity.uid, area, date, start_time, end_time, name, discription, MainActivity.location_id, capacity, categoryIDX + 1))
            }
        }


    }

    private fun createGroup(groupCreateModel : GroupCreateModel){
        val api=RetroInterface.create()
        api.groupCreate(groupCreateModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                    if(response.body() == "success") {
                        Toast.makeText(this@MakegroupActivity, "모임생성을 완료했습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else
                        Toast.makeText(this@MakegroupActivity, "모임생성을 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }
        })
    }
}