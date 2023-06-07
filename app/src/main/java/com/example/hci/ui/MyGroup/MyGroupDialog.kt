package com.example.hci.ui.MyGroup

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hci.MainActivity
import com.example.hci.R
import com.example.hci.RetroInterface
import com.example.hci.data.model.UserReviewWriteModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyGroupDialog(context : Context, private var item :MyGroupData) {
    private val dialog = Dialog(context, R.style.CustomDialog)
    private var context :Context = context

    fun showDialog()
    {
        dialog.setContentView(R.layout.groupstardialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val title :TextView = dialog.findViewById(R.id.title)
        val okbtn :TextView = dialog.findViewById(R.id.ok_btn)
        val cancelbtn :TextView = dialog.findViewById(R.id.cancel_btn)

        title.text = item.group_name + " 평가 하기"
        val star :ImageView = dialog.findViewById(R.id.imageView25)
        var cnt = 1

        star.setOnClickListener {
            if(cnt % 5 == 1) {
                star.setImageResource(R.drawable.star1)
                cnt = 2
            }
            else if(cnt % 5 == 2) {
                star.setImageResource(R.drawable.star2)
                cnt = 3
            }
            else if(cnt % 5 == 3) {
                star.setImageResource(R.drawable.star3)
                cnt = 4
            }
            else if(cnt % 5 == 4) {
                star.setImageResource(R.drawable.star4)
                cnt= 5
            }
            else if(cnt % 5 == 0) {
                star.setImageResource(R.drawable.star5)
                cnt = 1
            }
        }

        okbtn.setOnClickListener {
            cnt++
            if(cnt == 1)
                cnt = 5
            reviewGroup(UserReviewWriteModel(MainActivity.uid, cnt, item.group_id))

            dialog.cancel()
        }


        cancelbtn.setOnClickListener {
            dialog.cancel()
        }
    }

    private fun reviewGroup(userReviewWriteModel : UserReviewWriteModel){
        val api= RetroInterface.create()
        api.userReviewWrite(userReviewWriteModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful){
                    Log.d("Response:", response.body().toString())
                    if(response.body() == "update") {
                        Toast.makeText(context, "평가를 완료하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(context, "평가반영 실패 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "평가반영 실패 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(context, "평가반영 실패 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}