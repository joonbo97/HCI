package com.example.hci

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hci.data.model.GroupInviteModel
import java.text.SimpleDateFormat
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InviteFriendAdapter(private val context : Context) : RecyclerView.Adapter<InviteFriendAdapter.ViewHolder>() {
    var data = mutableListOf<InviteFriendData>()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val name : TextView = itemView.findViewById(R.id.group_name)
        private val location : TextView = itemView.findViewById(R.id.group_location)
        private val date : TextView = itemView.findViewById(R.id.group_date)
        private val time : TextView = itemView.findViewById(R.id.group_time)
        private val people : TextView = itemView.findViewById(R.id.group_people)
        private val invite :ImageView = itemView.findViewById(R.id.invite)

        fun bind(item : InviteFriendData)
        {
            name.text = item.name
            location.text = item.area


            //Date 설정
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = inputDateFormat.parse(item.date)
            val formattedDate = outputDateFormat.format(parsedDate)
            date.text = formattedDate

            // Start Time 설정
            val inputTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputTimeFormat = SimpleDateFormat("HH시 mm분", Locale.getDefault())
            val parsedStartTime = inputTimeFormat.parse(item.start_time)
            val formattedStartTime = outputTimeFormat.format(parsedStartTime)
            time.text = formattedStartTime

            // End Time 설정
            val parsedEndTime = inputTimeFormat.parse(item.end_time)
            val formattedEndTime = outputTimeFormat.format(parsedEndTime)
            time.text = time.text.toString() +" ~ " + formattedEndTime

            people.text = "${item.headcount}/${item.capacity}"

            invite.setOnClickListener {
                inviteGroup(GroupInviteModel(MainActivity.uid, item.friend_id, item.group_id))
                invite.alpha = 0.7f
                invite.isEnabled = false
            }

        }

    }

        override fun onCreateViewHolder(parent : ViewGroup, p1: Int): InviteFriendAdapter.ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.invitegroup_item, parent, false)
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

    private fun inviteGroup(groupInviteModel : GroupInviteModel){
        val api=RetroInterface.create()
        api.groupInvite(groupInviteModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                    if(response.body().toString() == "err")
                    {
                        Toast.makeText(context, "모임초대를 실패했습니다. 정원을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(context, "모임초대를 완료했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "모임초대를 실패했습니다. 이미 요청되었습니다.", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(context, "모임초대를 실패했습니다. 정원을 확인해주세요.", Toast.LENGTH_SHORT).show()

            }
        })
    }

}