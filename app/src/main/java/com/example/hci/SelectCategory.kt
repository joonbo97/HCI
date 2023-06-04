package com.example.hci

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.ImageView
import android.widget.TextView
import com.example.hci.data.model.GroupIdListModel
import com.example.hci.data.model.GroupIdListResult
import com.example.hci.data.model.GroupInfoModel
import com.example.hci.data.model.GroupInfoResult

class SelectCategory : AppCompatActivity() {
    lateinit var groupinfoadapter :GroupInfoAdapter
    val data = mutableListOf<GroupInfoResult>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        val categoryIDX = intent.getIntExtra("categoryIDX", 0)
        val temptext : TextView = this.findViewById(R.id.category_text)

        ChangeTitle(categoryIDX, temptext)

        val image1: ImageView = findViewById(R.id.back_image)

        image1.setOnClickListener {
            finish()
        }

        val ImageMakeGroup: ImageView = findViewById(R.id.makegroup_img)

        if(!MainActivity.flagLogin)//로그인 상태 아니라면 모임만들기 버튼 숨김
            ImageMakeGroup.visibility = View.INVISIBLE
        else
            ImageMakeGroup.visibility = View.VISIBLE

        ImageMakeGroup.setOnClickListener {
            val intent = Intent(this, MakegroupActivity::class.java)
            intent.putExtra("categoryIDX", categoryIDX)
            startActivity(intent)
        }



        //getGroupInfo()

        //TODO 리사이클러 뷰
        if(MainActivity.flagLogin)//로그인 상태라면
        {
            initRecycler()
        }
    }

    private fun getGroupidList(groupIdListModel : GroupIdListModel){
        val api=RetroInterface.create()
        api.groupIdList(groupIdListModel).enqueue(object : Callback<List<GroupIdListResult>> {
            override fun onResponse(call: Call<List<GroupIdListResult>>,response: Response<List<GroupIdListResult>>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<GroupIdListResult>>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }
        })
    }

    private fun getGroupInfo(groupInfoModel: GroupInfoModel){
        val api = RetroInterface.create()
        api.groupInfo(groupInfoModel).enqueue(object : Callback<List<GroupInfoResult>> {
            override fun onResponse(call: Call<List<GroupInfoResult>>, response: Response<List<GroupInfoResult>>) {
                if (response.isSuccessful) {
                    val groupInfoList = response.body()
                    groupinfoadapter.data.clear()
                    if (groupInfoList != null) {
                        groupinfoadapter.data.addAll(groupInfoList)
                    }
                    groupinfoadapter.notifyDataSetChanged()
                    Log.d("Response:", response.body().toString())
                } else {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<GroupInfoResult>>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }
        })
    }

    private fun initRecycler() {
        val recyclerView: RecyclerView = findViewById(R.id.SelectCategory_RecyclerView)

        groupinfoadapter = GroupInfoAdapter(this)
        recyclerView.adapter = groupinfoadapter

        data.apply {
            add(GroupInfoResult("1번 모임", 4.4, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("2번 모임", 3.4, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("3번 모임", 2.6, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("4번 모임", 1.4, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("5번 모임", 5.0, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("6번 모임", 4.6, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("7번 모임", 3.6, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("8번 모임", 2.2, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("9번 모임", 1.6, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
            add(GroupInfoResult("10번 모임", 5.0, "관리자", 3, 4, "설명", "2020-11-23",
                "2020-11-23 10:00:00", "2020-11-23 20:00:00", "광진구"))
        }

        groupinfoadapter.data.addAll(data)
        groupinfoadapter.notifyDataSetChanged()//변경감지
    }
}

fun ChangeTitle(categoryIDX: Int, TempText: TextView)//타이틀을 바꾸는 함수
{
    when(categoryIDX)
    {
        0 -> TempText.text = "운동/스포츠"
        1 -> TempText.text = "음악/악기"
        2 -> TempText.text = "여행"
        3 -> TempText.text = "스터디"
        4 -> TempText.text = "독서"
        5 -> TempText.text = "외국어"
        6 -> TempText.text = "문화/공연/축제"
        7 -> TempText.text = "게임/오락"
        8 -> TempText.text = "봉사"
        9 -> TempText.text = "반려동물"
        10-> TempText.text = "사진/영상"
        11-> TempText.text = "드라이브"
    }
}