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
import com.example.hci.R
import com.example.hci.RetroInterface
import com.example.hci.data.model.GroupReqAcceptModel
import com.example.hci.data.model.GroupReqRefuseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmGrouprequestAdapter(private val context : Context) :RecyclerView.Adapter<AlarmGrouprequestAdapter.ViewHolder>(){
    var data = mutableListOf<GroupInvite>()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val friend_name : TextView = itemView.findViewById(R.id.friend_name)
        private val friend_email : TextView = itemView.findViewById(R.id.friend_email)
        private val user_score_text : TextView = itemView.findViewById(R.id.user_score_text)
        private val group_name : TextView = itemView.findViewById(R.id.group_name)

        private val starimage : ImageView = itemView.findViewById(R.id.user_score)


        private val joinbtn : ImageView = itemView.findViewById(R.id.acceptbtn)
        private val rejectbtn : ImageView = itemView.findViewById(R.id.rejectbtn)


        fun bind(item : GroupInvite)
        {
            friend_name.text = item.sender_name
            friend_email.text = item.sender_email
            group_name.text = item.group_name

            user_score_text.text = String.format("%.1f", item.group_score)

            when {
                item.group_score == 5.0 -> starimage.setImageResource(R.drawable.star5)
                item.group_score >= 4.5 -> starimage.setImageResource(R.drawable.star4_5)
                item.group_score >= 4.0 -> starimage.setImageResource(R.drawable.star4)
                item.group_score >= 3.5 -> starimage.setImageResource(R.drawable.star3_5)
                item.group_score >= 3.0 -> starimage.setImageResource(R.drawable.star3)
                item.group_score >= 2.5 -> starimage.setImageResource(R.drawable.star2_5)
                item.group_score >= 2.0 -> starimage.setImageResource(R.drawable.star2)
                item.group_score >= 1.5 -> starimage.setImageResource(R.drawable.star1_5)
                item.group_score >= 1.0 -> starimage.setImageResource(R.drawable.star1)
                else -> starimage.setImageResource(R.drawable.star0_5)
            }


            joinbtn.setOnClickListener {
                acceptGroupReq(GroupReqAcceptModel(MainActivity.uid, item.sender_uid, item.group_id))

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }

            rejectbtn.setOnClickListener {
                refuseGroupReq(GroupReqRefuseModel(MainActivity.uid, item.sender_uid, item.group_id))

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, p1: Int) :ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.join_request_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmGrouprequestAdapter.ViewHolder, p1: Int) {
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

    private fun acceptGroupReq(groupReqAcceptModel : GroupReqAcceptModel){
        val api= RetroInterface.create()
        api.groupReqAccept(groupReqAcceptModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                    if(response.body().toString() == "success")
                    {
                        Toast.makeText(context, "모임에 참가요청을 수락하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(context, "모임에 참가요청 수락에 실패했습니다.", Toast.LENGTH_SHORT).show()

                    }
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "모임에 참가요청 수락에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(context, "모임에 참가요청 수락에 실패했습니다. 인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun refuseGroupReq(groupReqRefuseModel : GroupReqRefuseModel){
        val api=RetroInterface.create()
        api.groupReqRefuse(groupReqRefuseModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.body().toString() == "succeess")
                {
                    Toast.makeText(context, "모임에 참가요청을 거절하였습니다.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "모임에 참가요청 거절에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(context, "모임에 참가요청 거절에 실패했습니다. 인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
}