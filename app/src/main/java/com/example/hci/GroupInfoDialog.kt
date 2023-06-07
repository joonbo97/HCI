package com.example.hci

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.hci.data.model.GroupInfoResult2
import com.example.hci.data.model.GroupSendRequestModel
import java.text.SimpleDateFormat
import java.util.*

class GroupInfoDialog (context : Context, private val item : GroupInfoResult2)  {
    private val dialog = Dialog(context, R.style.CustomDialog)
    private var context :Context = context
    var bookmarkflag :Boolean = false

    fun showDialog()
    {
        dialog.setContentView(R.layout.group_info_layout)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val title :TextView = dialog.findViewById(R.id.info_title)
        val creater :TextView = dialog.findViewById(R.id.creater)
        val date :TextView = dialog.findViewById(R.id.group_date)
        val location :TextView = dialog.findViewById(R.id.group_location)
        val time :TextView = dialog.findViewById(R.id.group_time)
        val people :TextView = dialog.findViewById(R.id.group_people)
        val score :TextView = dialog.findViewById(R.id.score)
        val discription :TextView = dialog.findViewById(R.id.group_discription)

        val bookmark :ImageView = dialog.findViewById(R.id.bookmark)
        val joinbtn :ImageView = dialog.findViewById(R.id.joinbtn)
        val star :ImageView = dialog.findViewById(R.id.star)

        bookmark.setOnClickListener {
            if(bookmarkflag) {
                bookmark.setImageResource(R.drawable.top_bookmark)
                Toast.makeText(context, "즐겨찾기를 해제하였습니다.", Toast.LENGTH_SHORT).show()
                bookmarkflag = false
            }
            else {
                bookmark.setImageResource(R.drawable.bookmark_star)
                Toast.makeText(context, "즐겨찾기에 추가가하였습니다.", Toast.LENGTH_SHORT).show()
                bookmarkflag = true
            }
        }

        joinbtn.setOnClickListener {
            registerGroup(GroupSendRequestModel(MainActivity.uid, item.group_id))
        }

        title.text = item.name
        creater.text = "모임장 : " + item.creator
        location.text = "장소 : " + item.area
        //Date 설정
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = inputDateFormat.parse(item.date)
        val formattedDate = outputDateFormat.format(parsedDate)
        date.text = "날짜 : $formattedDate"

        // Start Time 설정
        val inputTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat("HH시 mm분", Locale.getDefault())
        val parsedStartTime = inputTimeFormat.parse(item.start_time)
        val formattedStartTime = outputTimeFormat.format(parsedStartTime)
        time.text = "시간 : " + formattedStartTime

        // End Time 설정
        val parsedEndTime = inputTimeFormat.parse(item.end_time)
        val formattedEndTime = outputTimeFormat.format(parsedEndTime)
        time.text = time.text.toString() + " ~ " +formattedEndTime

        people.text = "정원 : ${item.headcount}/${item.capacity} 명"

        score.text = String.format("%.1f", item.score)

        discription.text = item.description

        when {
            item.score == 5.0 -> star.setImageResource(R.drawable.star5)
            item.score >= 4.5 -> star.setImageResource(R.drawable.star4_5)
            item.score >= 4.0 -> star.setImageResource(R.drawable.star4)
            item.score >= 3.5 -> star.setImageResource(R.drawable.star3_5)
            item.score >= 3.0 -> star.setImageResource(R.drawable.star3)
            item.score >= 2.5 -> star.setImageResource(R.drawable.star2_5)
            item.score >= 2.0 -> star.setImageResource(R.drawable.star2)
            item.score >= 1.5 -> star.setImageResource(R.drawable.star1_5)
            item.score >= 1.0 -> star.setImageResource(R.drawable.star1)
            else -> star.setImageResource(R.drawable.star0_5)
        }
    }

    private fun registerGroup(groupSendRequestModel : GroupSendRequestModel){
        val api=RetroInterface.create()
        api.groupSendRequest(groupSendRequestModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    if(response.body() == "success")
                    {
                        Toast.makeText(context, "참가 요청을 보냈습니다.", Toast.LENGTH_SHORT).show()
                        dialog.cancel()
                    }
                    else
                    {
                        Toast.makeText(context, "참가 요청이 실패했습니다. 정원을 확인해주세요.", Toast.LENGTH_SHORT).show()

                    }
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "요청이 실패했습니다. 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(context, "요청이 실패했습니다. 인터넷연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}