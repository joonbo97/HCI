package com.example.hci.Alarm

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hci.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.hci.R
import com.example.hci.RetroInterface
import com.example.hci.data.model.FriendReqAcceptModel
import com.example.hci.data.model.FriendReqListResult2
import com.example.hci.data.model.FriendReqRefuse.FriendReqRefuseModel

class AlarmFrandAdapter(private val context : Context) : RecyclerView.Adapter<AlarmFrandAdapter.ViewHolder>(){
    var data = mutableListOf<FriendReqListResult2>()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val name :TextView = itemView.findViewById(R.id.friend_name)
        private val email :TextView = itemView.findViewById(R.id.friend_email)
        private val acceptbtn :ImageView = itemView.findViewById(R.id.acceptbtn)
        private val rejectbtn :ImageView = itemView.findViewById(R.id.rejectbtn)


        fun bind(item :FriendReqListResult2)
        {
            name.text = item.name
            email.text = item.email

            acceptbtn.setOnClickListener {
                //Toast.makeText(context, "친구요청을 수락하였습니다.", Toast.LENGTH_SHORT).show()

                acceptFriendReq(FriendReqAcceptModel(MainActivity.uid, item.from_id))

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }

            rejectbtn.setOnClickListener {
                refuseFriendReq(FriendReqRefuseModel(MainActivity.uid, item.from_id))

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }
        }
    }

    override fun onCreateViewHolder(parent :ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.alarm_friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(data[p1])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun removeData(position :Int)
    {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


    private fun acceptFriendReq(friendReqAcceptModel : FriendReqAcceptModel){
        val api= RetroInterface.create()
        api.friendReqAccept(friendReqAcceptModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful){
                    if(response.body() == "s")
                        Toast.makeText(context, "친구요청을 수락하였습니다.", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "친구요청을 수락을 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(context, "친구요청을 수락을 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "친구요청을 수락을 실패하였습니다. 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun refuseFriendReq(friendReqRefuseModel : FriendReqRefuseModel){
        val api=RetroInterface.create()
        api.friendReqRefuse(friendReqRefuseModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful){
                    if(response.body() == "s")
                        Toast.makeText(context, "친구요청을 거절하였습니다.", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "친구요청을 거절을 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(context, "${response.body()}친구요청을 거절을 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("ASDASD", response.body().toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "친구요청을 거절을 실패하였습니다. 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}