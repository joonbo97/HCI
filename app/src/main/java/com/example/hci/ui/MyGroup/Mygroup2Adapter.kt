package com.example.hci.ui.MyGroup

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hci.Alarm.AlarmFrandAdapter
import com.example.hci.R
import java.text.SimpleDateFormat
import java.util.*


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

            }

            downbtn.setOnClickListener {

            }


            //삭제
            /*val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
                removeData(position)*/

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
}