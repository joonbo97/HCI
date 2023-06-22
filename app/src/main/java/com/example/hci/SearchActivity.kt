package com.example.hci

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.hci.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    lateinit var groupinfoadapter :GroupInfoAdapter
    val data = mutableListOf<GroupInfoResult2>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val image1: ImageView = findViewById(R.id.back_image3)
        image1.setOnClickListener {
            finish()
        }

        val edit : EditText = findViewById(R.id.edit_group_search)

        edit.setOnClickListener {

            if(edit.length() == 0)
                Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            else {
                initRecycler(edit.text.toString())

                groupinfoadapter.notifyDataSetChanged()
            }
        }

    }

    private fun searchGroup(groupSearchModel : GroupSearchModel){
        val api=RetroInterface.create()
        api.groupSearch(groupSearchModel).enqueue(object : Callback<List<GroupSearchResult>> {
            override fun onResponse(call: Call<List<GroupSearchResult>>,response: Response<List<GroupSearchResult>>) {
                if(response.isSuccessful()){
                    if(response.body().toString() == "err")
                        Toast.makeText(this@SearchActivity, "해당 검색어에 해당하는 목록이 없습니다.", Toast.LENGTH_SHORT).show()
                    else {
                        Toast.makeText(this@SearchActivity, "${findViewById<EditText>(R.id.edit_group_search).text}가 들어간 모임을 검색합니다.", Toast.LENGTH_SHORT).show()
                        val groupIds = response.body()
                        groupIds?.let {
                            for (GroupIdListResult in groupIds) {
                                val id = GroupIdListResult.id

                                getGroupInfo(GroupInfoModel(id))
                            }
                        }
                    }
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<GroupSearchResult>>, t: Throwable) {
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

    private fun initRecycler(Search :String) {
        val recyclerView: RecyclerView = findViewById(R.id.SelectCategory_RecyclerView)

        groupinfoadapter = GroupInfoAdapter(this)
        recyclerView.adapter = groupinfoadapter

        searchGroup(GroupSearchModel(Search))


        groupinfoadapter.data.addAll(data)
        groupinfoadapter.notifyDataSetChanged()//변경감지
    }

}