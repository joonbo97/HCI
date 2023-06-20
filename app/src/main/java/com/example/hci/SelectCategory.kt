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
import android.widget.Toast
import com.example.hci.data.model.*

class SelectCategory : AppCompatActivity() {
    lateinit var groupinfoadapter :GroupInfoAdapter
    val data = mutableListOf<GroupInfoResult2>()

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

        if(MainActivity.flagLogin)//로그인 상태라면
        {
            initRecycler(categoryIDX)

            groupinfoadapter.notifyDataSetChanged()

        }
    }

    private fun getGroupidList(groupIdListModel : GroupIdListModel){
        val api=RetroInterface.create()
        api.groupIdList(groupIdListModel).enqueue(object : Callback<List<GroupIdListResult>> {
            override fun onResponse(call: Call<List<GroupIdListResult>>,response: Response<List<GroupIdListResult>>) {
                if(response.isSuccessful()){
                    val groupIds = response.body()
                    groupIds?.let {
                        for(GroupIdListResult in groupIds)
                        {
                            val id = GroupIdListResult.id

                            getGroupInfo(GroupInfoModel(id))
                        }
                    }
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
        api.groupInfo(groupInfoModel).enqueue(object : Callback<GroupInfoResult> {
            override fun onResponse(call: Call<GroupInfoResult>, response: Response<GroupInfoResult>) {
                if (response.isSuccessful) {
                    val groupInfoList = response.body()

                    if (groupInfoList != null) {
                        var master_name :String = ""

                        getUserInfo(UserModel(groupInfoList.creator.toInt())) { userName ->
                            if (userName != null)
                                master_name = userName.toString()


                            groupinfoadapter.data.add(GroupInfoResult2(groupInfoList.name, groupInfoList.score, master_name, groupInfoList.headcount, groupInfoList.capacity,
                                    groupInfoList.description, groupInfoList.date, groupInfoList.start_time, groupInfoList.end_time, groupInfoList.area, groupInfoModel.group_id))
                            groupinfoadapter.notifyDataSetChanged()
                        }
                    }
                    Log.d("Response:", response.body().toString())
                } else {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<GroupInfoResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }
        })
    }

    private fun getUserInfo(userModel: UserModel, callback: (String?) -> Unit){
        val api=RetroInterface.create()
        api.user(userModel).enqueue(object : Callback<UserResult> {
            override fun onResponse(call: Call<UserResult>, response: Response<UserResult>) {
                if(response.isSuccessful){
                    val userresult = response.body()
                    val userName = userresult?.name
                    callback(userName)
                }
                else
                    callback(null)
            }

            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                callback(null)
            }
        })
    }

    private fun initRecycler(categoryIDX :Int) {
        val recyclerView: RecyclerView = findViewById(R.id.SelectCategory_RecyclerView)

        groupinfoadapter = GroupInfoAdapter(this)
        recyclerView.adapter = groupinfoadapter

        getGroupidList(GroupIdListModel(categoryIDX+1, MainActivity.location_id))


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