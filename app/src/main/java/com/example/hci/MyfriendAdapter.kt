package com.example.hci

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class MyfriendAdapter(private val context : Context) : RecyclerView.Adapter<MyfriendAdapter.ViewHolder>() {
    var data = mutableListOf<MyfriendData>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val friend_name :TextView= view.findViewById(R.id.friend_name)
        val friend_email :TextView= view.findViewById(R.id.friend_email)

        val invite :ImageView = view.findViewById(R.id.upbutton)
        val chat :ImageView = view.findViewById(R.id.downbotton)

        fun bind(item : MyfriendData)
        {
            friend_name.text = item.friend_name
            friend_email.text = item.friend_email

            invite.setOnClickListener {
                val dialog = InviteFriendDialog(context, item.friend_id)
                dialog.showDialog()
            }

            chat.setOnClickListener {
                val intent = Intent(context, ChattingActivity::class.java)
                intent.putExtra("friendname", item.friend_name)
                context.startActivity(intent)
            }

        }
    }


    override fun onCreateViewHolder(parent : ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.myfriend_item, parent, false)
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