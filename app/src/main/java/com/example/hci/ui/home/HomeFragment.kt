package com.example.hci.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import com.example.hci.*
import com.example.hci.ui.login.LoginActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var categoryIDX = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val flagLogin = MainActivity.flagLogin
        val flagAlarm = MainActivity.flagAlarm

        val text1 :TextView = root.findViewById(R.id.text0)
        val ImageAlarm :ImageView = root.findViewById(R.id.alarm_image)
        val ImageAlarmNew :ImageView = root.findViewById(R.id.new_alarm_image)

        if(!flagLogin)
        {
            text1.text = "로그인하세요"
        }

        if(flagAlarm)//알람이 있으면 true
        {
            ImageAlarm.visibility = View.INVISIBLE
            ImageAlarmNew.visibility = View.VISIBLE
        }
        else
        {
            ImageAlarm.visibility = View.VISIBLE
            ImageAlarmNew.visibility = View.INVISIBLE
        }
        //상단 로그인, 지역 버튼 클릭 이벤트
        text1.setOnClickListener {
            if(!flagLogin)//로그인 상태가 아니라면
            {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            else
            {
                val intent = Intent(activity, SetlocationActivity::class.java)
                startActivity(intent)
            }
        }
        //카테고리 버튼
        val ImageHealth: ImageView = root.findViewById(R.id.icon_image)
        val ImageMusic: ImageView = root.findViewById(R.id.music_image)
        val ImageTravel: ImageView = root.findViewById(R.id.travel_image)
        val ImageStudy: ImageView = root.findViewById(R.id.study_image)
        val ImageBook: ImageView = root.findViewById(R.id.book_image)
        val ImageLangugage: ImageView = root.findViewById(R.id.language_image)
        val ImageCulcturl: ImageView = root.findViewById(R.id.culcturl_image)
        val ImageGame: ImageView = root.findViewById(R.id.game_image)
        val ImageVolunteer: ImageView = root.findViewById(R.id.volunteer_image)
        val ImagePet: ImageView = root.findViewById(R.id.pet_image)
        val ImagePhoto: ImageView = root.findViewById(R.id.photo_image)
        val ImageDrive: ImageView = root.findViewById(R.id.drive_image)

        //상단 search, bookmark 버튼
        val ImageSearch: ImageView = root.findViewById(R.id.search_image)
        val ImageBookmark: ImageView = root.findViewById(R.id.star_bookmark_image)


        //카테고리 click listener
        ImageHealth.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 0
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageMusic.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 1
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageTravel.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 2
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageStudy.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 3
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageBook.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 4
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageLangugage.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 5
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageCulcturl.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 6
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageGame.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 7
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageVolunteer.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 8
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImagePet.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 9
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImagePhoto.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 10
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }
        ImageDrive.setOnClickListener {
            val intent = Intent(activity, SelectCategory::class.java)
            categoryIDX = 11
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }



        //상단 버튼 click listener

        //알람 2가지 case일 경우
        ImageAlarm.setOnClickListener{
            val intent = Intent(activity, AlarmActivity::class.java)
            startActivity(intent)
        }
        ImageAlarmNew.setOnClickListener{
            val intent = Intent(activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        //bookmark 버튼
        ImageBookmark.setOnClickListener {
            val intent = Intent(activity, BookMarkActivity::class.java)
            startActivity(intent)
        }

        //search 버튼
        ImageSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }

        return root
    }
}