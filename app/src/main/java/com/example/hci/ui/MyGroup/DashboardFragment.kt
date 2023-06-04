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
import android.widget.ImageView
import com.example.hci.R
import com.example.hci.Searchidpw.SearchIDFragment
import com.example.hci.Searchidpw.SearchIDPWAdapter
import com.example.hci.Searchidpw.SearchPWFragment

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

        val imageView :ImageView = root.findViewById(R.id.imageView15)
        val tablayout: TabLayout = root.findViewById(R.id.tabLayout)
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0?.position
                when(position){
                    0 -> imageView.setImageResource(R.drawable.gara1)
                    1 -> imageView.setImageResource(R.drawable.gara2)
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

        })

        return root
    }

}