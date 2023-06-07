package com.example.hci.ui.MyGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager

import com.example.hci.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val viewpager: ViewPager = root.findViewById(R.id.viewpager0)
        val tablayout: TabLayout = root.findViewById(R.id.tabLayout)

        //adapter와 viewpager를 연결하여 보여준다.
        val adapter = MyGroupAdapter(childFragmentManager)
        adapter.addFragment(Mygroup1Fragment(), "참가 중")//title이 바뀌면 tab도 바뀐다.
        adapter.addFragment(Mygroup2Fragment(), "운영 중")

        viewpager.adapter = adapter
        tablayout.setupWithViewPager(viewpager)

        return root
    }

}