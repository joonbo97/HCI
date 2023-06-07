package com.example.hci.Alarm

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.hci.R

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val image1: ImageView = findViewById(R.id.back_image)
        image1.setOnClickListener {
            finish()
        }

        //TODO tablelayout에서 선택된 idx값을 통해 어떤 알림을 recycleview를 통해 보여줄 것인지 확인후 보여준다.

        val viewpager: ViewPager =findViewById(R.id.viewpager0)
        val tablayout: TabLayout =findViewById(R.id.tabLayout)

        //adapter와 viewpager를 연결하여 보여준다.
        val adapter = AlarmAdapter(supportFragmentManager)
        adapter.addFragment(AlarmFriendFragment(), "친구 요청")//title이 바뀌면 tab도 바뀐다.
        adapter.addFragment(AlarmGroupinviteFragment(), "모임 초대")
        adapter.addFragment(AlarmGrouprequestFragment(), "가입 요청")

        viewpager.adapter = adapter
        tablayout.setupWithViewPager(viewpager)

    }
}