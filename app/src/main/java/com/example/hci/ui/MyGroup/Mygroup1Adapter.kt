package com.example.hci.ui.MyGroup

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
import com.example.hci.data.model.GroupExitModel
import java.text.SimpleDateFormat
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Mygroup1Adapter(private val context : Context) : RecyclerView.Adapter<Mygroup1Adapter.ViewHolder>() {
    var data = mutableListOf<MyGroupData>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val group_name : TextView = itemView.findViewById(R.id.group_name)
        private val group_location : TextView = itemView.findViewById(R.id.group_location)
        private val group_date : TextView = itemView.findViewById(R.id.group_name2)
        private val group_time : TextView = itemView.findViewById(R.id.group_time)
        private val group_people : TextView = itemView.findViewById(R.id.group_people)

        private val upbtn : ImageView = itemView.findViewById(R.id.upbutton)
        private val downbtn : ImageView = itemView.findViewById(R.id.downbotton)
        fun bind(item :MyGroupData)
        {
            upbtn.setImageResource(R.drawable.mygroup_validation)
            downbtn.setImageResource(R.drawable.mygroup_quit)

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
            group_time.text = formattedStartTime

            // End Time 설정
            val parsedEndTime = inputTimeFormat.parse(item.group_end)
            val formattedEndTime = outputTimeFormat.format(parsedEndTime)
            group_time.text = group_time.text.toString() + " ~ " + formattedEndTime

            group_people.text = "${item.group_headcount}/${item.group_capacity}"


            upbtn.setOnClickListener {
                Toast.makeText(context, "모임에 평가를 진행해주세요", Toast.LENGTH_SHORT).show()

                val dialog = MyGroupDialog(context, item)
                dialog.showDialog()

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }

            downbtn.setOnClickListener {
                Toast.makeText(context, "모임을 탈퇴합니다.", Toast.LENGTH_SHORT).show()

                exitGroup(GroupExitModel(item.group_id,MainActivity.uid))

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }
        }
    }

    private fun exitGroup(groupExitModel : GroupExitModel){
        val api= RetroInterface.create()
        api.groupExit(groupExitModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                    if (response.body() == "d")
                        Toast.makeText(context, "모임을 탈퇴합니다.", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "탈퇴요청이 실패했습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "탈퇴요청이 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(context, "탈퇴요청이 실패했습니다. 인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show()

            }
        })
    }


    override fun onCreateViewHolder(parent :ViewGroup, p1: Int): Mygroup1Adapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mygroup_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Mygroup1Adapter.ViewHolder, p1: Int) {
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
}