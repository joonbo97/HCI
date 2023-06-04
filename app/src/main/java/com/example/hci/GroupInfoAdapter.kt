package com.example.hci

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hci.data.model.GroupInfoResult
import java.text.SimpleDateFormat
import java.util.*

class GroupInfoAdapter (private val context : Context) : RecyclerView.Adapter<GroupInfoAdapter.ViewHolder>(){
    var data = mutableListOf<GroupInfoResult>()

    inner class ViewHolder(view :View) : RecyclerView.ViewHolder(view){
        private val name :TextView = itemView.findViewById(R.id.group_name)
        private val location :TextView = itemView.findViewById(R.id.group_location)
        private val date :TextView = itemView.findViewById(R.id.group_date)
        private val start :TextView = itemView.findViewById(R.id.group_start)
        private val end :TextView = itemView.findViewById(R.id.group_end)
        private val score :TextView = itemView.findViewById(R.id.group_score_text)
        private val score_image :ImageView = itemView.findViewById(R.id.group_score)
        private val people :TextView = itemView.findViewById(R.id.group_people)

        fun bind(item :GroupInfoResult)
        {
            name.text = item.name
            location.text = item.area
            date.text = item.date


            //start time 설정
            var inputDateTime = item.start_time

            var dateTimeParts = inputDateTime.split(" ")
            var timePart = dateTimeParts[1]

            var timeParts = timePart.split(":")
            var hour = timeParts[0]
            var minute = timeParts[1]

            start.text = "${hour}시 ${minute}분"

            //end time 설정
            inputDateTime = item.end_time

            dateTimeParts = inputDateTime.split(" ")
            timePart = dateTimeParts[1]

            timeParts = timePart.split(":")
            hour = timeParts[0]
            minute = timeParts[1]

            end.text = "${hour}시 ${minute}분"

            people.text = "${item.headcount}/${item.capacity}"

            score.text = String.format("%.1f", item.score)



            if(item.score == 5.0)
                score_image.setImageResource(R.drawable.star5)
            else if(item.score >= 4.5)
                score_image.setImageResource(R.drawable.star4_5)
            else if(item.score >= 4.0)
                score_image.setImageResource(R.drawable.star4)
            else if(item.score >= 3.5)
                score_image.setImageResource(R.drawable.star3_5)
            else if(item.score >= 3.0)
                score_image.setImageResource(R.drawable.star3)
            else if(item.score >= 2.5)
                score_image.setImageResource(R.drawable.star2_5)
            else if(item.score >= 2.0)
                score_image.setImageResource(R.drawable.star2)
            else if(item.score >= 1.5)
                score_image.setImageResource(R.drawable.star1_5)
            else if(item.score >= 1.0)
                score_image.setImageResource(R.drawable.star1)
            else
                score_image.setImageResource(R.drawable.star0_5)
        }
    }

    override fun onCreateViewHolder(parent :ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_info_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(data[p1])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}