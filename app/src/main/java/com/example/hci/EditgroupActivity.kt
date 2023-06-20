package com.example.hci

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.hci.data.model.GroupModifyModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditgroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editgroup)



        val Edit_Area : EditText = findViewById(R.id.local_edit)
        val Edit_DateYear : EditText = findViewById(R.id.date_year_edit)
        val Edit_DateMonth : EditText = findViewById(R.id.date_month_edit)
        val Edit_DateDay : EditText = findViewById(R.id.date_day_edit)
        val Edit_StartHour : EditText = findViewById(R.id.starttime_hour_edit)
        val Edit_StartMin : EditText = findViewById(R.id.starttime_min_edit)
        val Edit_EndHour : EditText = findViewById(R.id.endtime_hour_edit)
        val Edit_EndMin : EditText = findViewById(R.id.endtime_min_edit)
        val Edit_Name : EditText = findViewById(R.id.groupname_edit)
        val Edit_discription : EditText = findViewById(R.id.groupdiscription_edit)
        val Edit_capacity : EditText = findViewById(R.id.maxpeople_edit)
        val done : ImageView = findViewById(R.id.imageView17)

        Edit_Area.setText(intent.getStringExtra("group_location"))
        Edit_DateYear.setText(intent.getStringExtra("group_date_year"))
        Edit_DateMonth.setText(intent.getStringExtra("group_date_month"))
        Edit_DateDay.setText(intent.getStringExtra("group_date_day"))
        Edit_StartHour.setText(intent.getStringExtra("group_start_hour"))
        Edit_StartMin.setText(intent.getStringExtra("group_start_min"))
        Edit_EndHour.setText(intent.getStringExtra("group_end_hour"))
        Edit_EndMin.setText(intent.getStringExtra("group_end_min"))
        Edit_Name.setText(intent.getStringExtra("group_name"))
        Edit_discription.setText(intent.getStringExtra("group_discription"))
        Edit_capacity.setText(intent.getStringExtra("group_capacity"))

        fun isempty() :Boolean{ //비었으면 true
            return (Edit_Area.length() == 0) || (Edit_DateYear.length() == 0) || (Edit_DateMonth.length() == 0) || (Edit_DateDay.length() == 0) || (Edit_StartHour.length() == 0) || (Edit_StartMin.length() == 0) ||
                    (Edit_EndHour.length() == 0) || (Edit_EndMin.length() == 0) || (Edit_Name.length() == 0) || (Edit_discription.length() == 0)
        }

        done.setOnClickListener {
            if(isempty())
                Toast.makeText(this, "빈칸이 있습니다. 모두 작성해주세요.", Toast.LENGTH_SHORT).show()
            else if(Edit_DateYear.length() != 4)
                Toast.makeText(this, "년도를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            else if(Edit_DateMonth.text.toString().toInt() > 12)
                Toast.makeText(this, "월을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            else if(Edit_DateDay.text.toString().toInt() > 31)
                Toast.makeText(this, "일을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            else if(Edit_StartHour.text.toString().toInt() > 23)
                Toast.makeText(this, "시작 시간을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            else if(Edit_EndHour.text.toString().toInt() > 23)
                Toast.makeText(this, "종료 시간을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            else if(Edit_StartMin.text.toString().toInt() > 59)
                Toast.makeText(this, "시작 시간을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            else if(Edit_EndMin.text.toString().toInt() > 59)
                Toast.makeText(this, "시작 시간을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            else
            {
                val area = Edit_Area.text.toString()
                val group_id = intent.getStringExtra("group_id")
                val date_year = Edit_DateYear.text.toString()
                val date_month = Edit_DateMonth.text.toString()
                if(date_month.length == 1)
                    date_month.padStart(2, '0')

                val date_day = Edit_DateDay.text.toString()
                if(date_day.length == 1)
                    date_day.padStart(2, '0')

                val start_hour = Edit_StartHour.text.toString()
                if(start_hour.length == 1)
                    start_hour.padStart(2, '0')

                val start_min = Edit_StartMin.text.toString()
                if(start_min.length == 1)
                    start_min.padStart(2, '0')

                val end_hour = Edit_EndHour.text.toString()
                if(end_hour.length == 1)
                    end_hour.padStart(2, '0')

                val end_min = Edit_EndMin.text.toString()
                if(end_min.length == 1)
                    end_min.padStart(2, '0')

                val name = Edit_Name.text.toString()
                val discription = Edit_discription.text.toString()
                val capacity = Edit_capacity.text.toString().toInt()
                val date = "$date_year-$date_month-$date_day"
                val start_time = "$date $start_hour:$start_min:00"
                val end_time = "$date $end_hour:$end_min:00"


                modifyGroup(GroupModifyModel(group_id!!.toInt(), MainActivity.uid, area, date, start_time, end_time, name, discription, capacity))
            }
        }

    }


    private fun modifyGroup(groupModifyModel : GroupModifyModel){
        val api=RetroInterface.create()
        api.groupModify(groupModifyModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                    if(response.body().toString() == "success") {
                        Toast.makeText(this@EditgroupActivity, "모임 수정을 완료했습니다.", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                    else
                        Toast.makeText(this@EditgroupActivity, "모임 수정을 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(this@EditgroupActivity, "모임 수정을 실패했습니다.", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(this@EditgroupActivity, "요청을 실패했습니다. 인터넷을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }


}