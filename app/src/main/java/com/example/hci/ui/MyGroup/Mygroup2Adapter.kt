package com.example.hci.ui.MyGroup

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hci.*
import com.example.hci.data.model.GroupDeleteModel
import com.example.hci.data.model.GroupInfoResult2
import java.text.SimpleDateFormat
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Mygroup2Adapter(private val context : Context) : RecyclerView.Adapter<Mygroup2Adapter.ViewHolder>() {
    var data = mutableListOf<MyGroupData>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val group_name :TextView = itemView.findViewById(R.id.group_name)
        private val group_location :TextView = itemView.findViewById(R.id.group_location)
        private val group_date :TextView = itemView.findViewById(R.id.group_name2)
        private val group_time :TextView = itemView.findViewById(R.id.group_time)
        private val group_people :TextView = itemView.findViewById(R.id.group_people)

        private val upbtn :ImageView = itemView.findViewById(R.id.upbutton)
        private val downbtn :ImageView = itemView.findViewById(R.id.downbotton)

        fun bind(item :MyGroupData)
        {
            upbtn.setImageResource(R.drawable.mygroup_edit)
            downbtn.setImageResource(R.drawable.mygroup_delete)

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
                val intent = Intent(context, EditgroupActivity::class.java)


                intent.putExtra("group_id", item.group_id.toString())
                intent.putExtra("group_name", group_name.text.toString())
                intent.putExtra("group_location", group_location.text.toString())
                intent.putExtra("group_discription", item.group_discription)

                val date = group_date.text.toString()
                val inputDateFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val parsedDate2 = inputDateFormat2.parse(date)
                val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(parsedDate2)
                val month = SimpleDateFormat("MM", Locale.getDefault()).format(parsedDate2)
                val day = SimpleDateFormat("dd", Locale.getDefault()).format(parsedDate2)

                val yearString: String = year
                val monthString: String = month
                val dayString: String = day

                intent.putExtra("group_date_year", yearString)
                intent.putExtra("group_date_month", monthString)
                intent.putExtra("group_date_day", dayString)
                intent.putExtra("group_date_day", dayString)

                val timeRangeString = group_time.text.toString()
                val timePattern = "(\\d{2})시\\s(\\d{2})분".toRegex()

                val matches = timePattern.findAll(timeRangeString).toList()
                if (matches.size == 2) {
                    val match1 = matches[0]
                    val match2 = matches[1]

                    val hour1 = match1.groupValues[1]
                    val minute1 = match1.groupValues[2]

                    val hour2 = match2.groupValues[1]
                    val minute2 = match2.groupValues[2]

                    intent.putExtra("group_start_hour", hour1)
                    intent.putExtra("group_start_min", minute1)
                    intent.putExtra("group_end_hour", hour2)
                    intent.putExtra("group_end_min", minute2)
                }


                intent.putExtra("group_capacity", item.group_capacity.toString())

                context.startActivity(intent)
            }

            downbtn.setOnClickListener {
                deleteGroup(GroupDeleteModel(item.group_id, MainActivity.uid))
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    removeData(position)
            }

            itemView.setOnClickListener {
                val dialog = GroupInfoDialog(context, GroupInfoResult2(
                    item.group_name,
                    item.score,
                    item.creator,
                    item.group_headcount,
                    item.group_capacity,
                    item.group_discription,
                    item.group_date,
                    item.group_start,
                    item.group_end,
                    item.group_location,
                    item.group_id), false)
                dialog.showDialog()
            }


        }
    }

    override fun onCreateViewHolder(parent :ViewGroup, p1: Int): Mygroup2Adapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mygroup_item, parent, false)
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








    private fun deleteGroup(groupDeleteModel : GroupDeleteModel){
        val api= RetroInterface.create()
        api.groupDelete(groupDeleteModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())

                    if(response.body().toString() == "d")
                        Toast.makeText(context, "모임해체를 완료했습니다.", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "모임해체를 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(context, "모임해체를 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "요청을 실패했습니다. 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}