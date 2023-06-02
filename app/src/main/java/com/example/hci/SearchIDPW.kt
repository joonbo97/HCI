package com.example.hci

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.hci.Searchidpw.SearchIDFragment
import com.example.hci.Searchidpw.SearchIDPWAdapter
import com.example.hci.Searchidpw.SearchPWFragment

class SearchIDPW : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_i_d_p_w)

        val imageback : ImageView = findViewById(R.id.back_image)
        imageback.setOnClickListener {
            finish()
        }

        val viewpager: ViewPager =findViewById(R.id.viewpager0)
        val tablayout: TabLayout =findViewById(R.id.tabLayout)

        //adapter와 viewpager를 연결하여 보여준다.
        val adapter = SearchIDPWAdapter(supportFragmentManager)
        adapter.addFragment(SearchIDFragment(), "아이디 찾기")//title이 바뀌면 tab도 바뀐다.
        adapter.addFragment(SearchPWFragment(), "비밀번호 찾기")

        viewpager.adapter = adapter
        tablayout.setupWithViewPager(viewpager)


    }
}