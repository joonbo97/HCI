package com.example.hci

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hci.data.model.FriendReqModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendDialog (context :Context) {

    private val dialog = Dialog(context, R.style.CustomDialog)
    private lateinit var onClickListener: OnDialogClickListener
    private var context :Context = context


    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog()
    {
        dialog.setContentView(R.layout.add_friend_layout)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val okbtn : Button = dialog.findViewById(R.id.ok_btn)
        val cancelbtn :Button = dialog.findViewById(R.id.cancel_btn)

        val editfriendname :EditText = dialog.findViewById(R.id.edit_addfriend_name)
        val editfriendemail :EditText = dialog.findViewById(R.id.edit_addfriend_email)

        okbtn.setOnClickListener {
            val friend_name :String = editfriendname.text.toString()
            val friend_email :String = editfriendemail.text.toString()

            requestFriend(FriendReqModel(MainActivity.uid ,friend_name ,friend_email))
        }

        cancelbtn.setOnClickListener {
            dialog.cancel()
        }
    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }



    private fun requestFriend(friendReqModel : FriendReqModel){
        val api=RetroInterface.create()
        api.friendReq(friendReqModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    Log.d("Response:", response.body().toString())
                    Toast.makeText(context, "${friendReqModel.name}님에게 친구 요청을 보냈습니다.", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "${response.body()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(context, "친구추가에 실패하였습니다.", Toast.LENGTH_SHORT).show() //연결 문제인데 자꾸 실패가 일로옴
            }
        })
    }
}