package com.example.hci.ui.MyGroup

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hci.MainActivity
import com.example.hci.R
import com.example.hci.RetroInterface
import com.example.hci.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Mygroup2Fragment : Fragment() {
    lateinit var mygroup2adapter :Mygroup2Adapter
    val data = mutableListOf<MyGroupData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_mygroup2, container, false)

        val recyclerView :RecyclerView = root.findViewById(R.id.mygroup2_recycler)

        mygroup2adapter = Mygroup2Adapter(requireContext())
        recyclerView.adapter = mygroup2adapter

        getOperatedGroup(GroupCreatedModel(MainActivity.uid))

        mygroup2adapter.data.addAll(data)
        mygroup2adapter.notifyDataSetChanged()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getOperatedGroup(groupCreatedModel : GroupCreatedModel){
        val api= RetroInterface.create()
        api.groupCreated(groupCreatedModel).enqueue(object : Callback<List<GroupCreatedResult>> {
            override fun onResponse(call: Call<List<GroupCreatedResult>>,response: Response<List<GroupCreatedResult>>) {
                if(response.isSuccessful){
                    Log.d("Response:", response.body().toString())
                    val groupidlist = response.body()

                    groupidlist?.let {
                        for(GroupJoineddResult in groupidlist)
                        {
                            val groupid = GroupJoineddResult.group_id

                            getGroupInfo(GroupInfoModel(groupid))
                        }
                    }
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<GroupCreatedResult>>, t: Throwable) {
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
                        var master_name: String = ""

                        getUserInfo(UserModel(groupInfoList.creator.toInt())) { userName ->
                            if (userName != null)
                                master_name = userName.toString()


                            mygroup2adapter.data.add(
                                MyGroupData(
                                    groupInfoModel.group_id,
                                    groupInfoList.name,
                                    groupInfoList.area,
                                    groupInfoList.date,
                                    groupInfoList.capacity,
                                    groupInfoList.headcount,
                                    groupInfoList.start_time,
                                    groupInfoList.end_time,
                                    groupInfoList.description,
                                    groupInfoList.score,
                                    master_name
                                )
                            )
                            mygroup2adapter.notifyDataSetChanged()
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
}