package com.example.hci.ui.MyProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import com.example.hci.*
import com.example.hci.ui.login.LoginActivity
import java.io.FileNotFoundException
import java.io.InputStream

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        val flagLogin = MainActivity.flagLogin
        val nameText :TextView= root.findViewById(R.id.myname_text)
        val locationText :TextView= root.findViewById(R.id.mylocation_text)
        val emailText :TextView= root.findViewById(R.id.myemail_text)

        val editimage :ImageView = root.findViewById(R.id.edit_btn)
        val chatimage :ImageView = root.findViewById(R.id.chat_image)
        val friendimage :ImageView = root.findViewById(R.id.friend_image)



        val starimg :ImageView = root.findViewById(R.id.star)


        fun setstar(point :Double)//점수 입력하면 별을 세팅하는 함수
        {
            if(point == 5.0)
                starimg.setImageResource(R.drawable.star5)
            else if(point >= 4.5)
                starimg.setImageResource(R.drawable.star4_5)
            else if(point >= 4.0)
                starimg.setImageResource(R.drawable.star4)
            else if(point >= 3.5)
                starimg.setImageResource(R.drawable.star3_5)
            else if(point >= 3.0)
                starimg.setImageResource(R.drawable.star3)
            else if(point >= 2.5)
                starimg.setImageResource(R.drawable.star2_5)
            else if(point >= 2.0)
                starimg.setImageResource(R.drawable.star2)
            else if(point >= 1.5)
                starimg.setImageResource(R.drawable.star1_5)
            else if(point >= 1.0)
                starimg.setImageResource(R.drawable.star1)
            else
                starimg.setImageResource(R.drawable.star0_5)
        }


        if(!flagLogin)//Login 상태가 아니라면
        {
            nameText.text = "로그인을 하세요"
            locationText.text = "-"
            emailText.text = "-"

            editimage.visibility = View.INVISIBLE
            chatimage.visibility = View.INVISIBLE
            friendimage.visibility = View.INVISIBLE

            root.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        else
        {
            //버튼들 활성화
            editimage.visibility = View.VISIBLE
            chatimage.visibility = View.VISIBLE
            friendimage.visibility = View.VISIBLE

            nameText.text = MainActivity.name
            locationText.text = MainActivity.city + " " +MainActivity.district
            emailText.text = MainActivity.email

            val score :TextView = root.findViewById(R.id.myscore_text)
            score.text = String.format("%.1f", MainActivity.score)

            setstar(MainActivity.score)
        }

        friendimage.setOnClickListener {
            val intent = Intent(activity, MyfriendActivity::class.java)
            startActivity(intent)
        }
        chatimage.setOnClickListener {
            val intent = Intent(activity, ChatActivity::class.java)
            startActivity(intent)
        }

        editimage.setOnClickListener {
            val intent = Intent(activity, EditprofileActivity::class.java)
            startActivity(intent)
        }

        return root
    }
}