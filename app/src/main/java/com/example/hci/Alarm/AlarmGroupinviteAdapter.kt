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
import com.example.hci.data.model.GroupInviteAcceptModel
import com.example.hci.data.model.GroupInviteRefuseModel
import java.text.SimpleDateFormat
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmGroupinviteAdapter(private val context : Context) : RecyclerView.Adapter<AlarmGroupinviteAdapter.ViewHolder>(){
    var data = mutableListOf<GroupInvite>()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val name : TextView = itemView.findViewById(R.id.sender_name)
        private val group_name :TextView = itemView.findViewById(R.id.group_name)
        private val group_location :TextView = itemView.findViewById(R.id.group_location)
        private val group_date :TextView = itemView.findViewById(R.id.group_date)
        private val group_start :TextView = itemView.findViewById(R.id.group_start)
        private val group_end :TextView = itemView.findViewById(R.id.group_end)
        private val group_scoretext :TextView = itemView.findViewById(R.id.group_score_text)
        private val group_people :TextView = itemView.findViewById(R.id.group_people)

        private val starimage :ImageView = itemView.findViewById(R.id.group_score)
        private val joinbtn :ImageView = itemView.findViewById(R.id.joinbtn)
        private val rejectbtn :ImageView = itemView.findViewById(R.id.rejectbtn)


        fun bind(item : GroupInvite)
        {
            name.text = item.sender_name
            group_name.text = item.group_name
            group_location.text = item.group_location

            //Date 설정
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = inputDateFormat.parse(item.group_date)
            val formattedDate = outputDateFormat.format(parsedDate)
            group_date.text = formattedDate

            // Start Time 설정
            val inputTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputTimeFormat = SimpleDateFormat("HH시 mm분", Locale.getDefault())
            val parsedStartTime = inputTimeFormat.parse(item.group_start)
            val formattedStartTime = outputTimeFormat.format(parsedStartTime)
            group_start.text = formattedStartTime

            // End Time 설정
            val parsedEndTime = inputTimeFormat.parse(item.group_end)
            val formattedEndTime = outputTimeFormat.format(parsedEndTime)
            group_end.text = formattedEndTime

            group_people.text = "정원 : ${item.group_headcount}/${item.group_capacity}"

            group_scoretext.text = String.format("%.1f", item.group_score)

            if(item.group_score == 5.0)
                starimage.setImageResource(R.drawable.star5)
            else if(item.group_score >= 4.5)
                starimage.setImageResource(R.drawable.star4_5)
            else if(item.group_score >= 4.0)
                starimage.setImageResource(R.drawable.star4)
            else if(item.group_score >= 3.5)
                starimage.setImageResource(R.drawable.star3_5)
            else if(item.group_score >= 3.0)
                starimage.setImageResource(R.drawable.star3)
            else if(item.group_score >= 2.5)
                starimage.setImageResource(R.drawable.star2_5)
            else if(item.group_score >= 2.0)
                starimage.setImageResource(R.drawable.star2)
            else if(item.group_score >= 1.5)
                starimage.setImageResource(R.drawable.star1_5)
            else if(item.group_score >= 1.0)
                starimage.setImageResource(R.drawable.star1)
            else
                starimage.setImageResource(R.drawable.star0_5)


            joinbtn.setOnClickListener {
                acceptInviteGroup(GroupInviteAcceptModel(MainActivity.uid, item.group_id))

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }

            rejectbtn.setOnClickListener {
                refuseInviteGroup(GroupInviteRefuseModel(MainActivity.uid, item.group_id))

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.alarm_friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmGroupinviteAdapter.ViewHolder, p1: Int) {
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

    private fun acceptInviteGroup(groupInviteAcceptModel : GroupInviteAcceptModel){
        val api= RetroInterface.create()
        api.groupInviteAccept(groupInviteAcceptModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                    if(response.body() == "success")
                        Toast.makeText(context, "모임에 참가하였습니다.", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "모임에 참가에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "모임에 참가에 실패하였습니다.", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "모임에 참가에 실패하였습니다. 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun refuseInviteGroup(groupInviteRefuseModel : GroupInviteRefuseModel){
        val api=RetroInterface.create()
        api.groupInviteRefuse(groupInviteRefuseModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.body() == "success")
                    Toast.makeText(context, "모임에 초대를 거절하였습니다.", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "모임에 초대 거절에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(context, "모임에 초대 거절에 실패하였습니다. 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}