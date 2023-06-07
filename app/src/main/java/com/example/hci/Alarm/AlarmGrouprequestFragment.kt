package com.example.hci.Alarm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hci.MainActivity
import com.example.hci.R
import com.example.hci.RetroInterface
import com.example.hci.data.model.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmGrouprequestFragment : Fragment() {
    lateinit var alarmGrouprequestAdapter: AlarmGrouprequestAdapter
    val data = mutableListOf<GroupInvite>()

    val groupInvite = GroupInvite(0,"", "",
        0,"", "", "",
        "", "", 0, 0,0.0)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_alarm_grouprequest, container, false)

        val recyclerView : RecyclerView = root.findViewById(R.id.alarm_group_request_recycler)
        alarmGrouprequestAdapter = AlarmGrouprequestAdapter(requireContext())
        recyclerView.adapter = alarmGrouprequestAdapter

        getGroupReqList(GroupReqListModel(MainActivity.uid))

        alarmGrouprequestAdapter.data.addAll(data)
        alarmGrouprequestAdapter.notifyDataSetChanged()


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun getGroupReqList(groupReqListModel : GroupReqListModel){
        val api= RetroInterface.create()
        api.groupReqList(groupReqListModel).enqueue(object : Callback<List<GroupReqListResult>> {
            override fun onResponse(call: Call<List<GroupReqListResult>>,response: Response<List<GroupReqListResult>>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                    val grouprequest = response.body()

                    grouprequest?.let {
                        for(GroupReqListResult in grouprequest)
                        {
                            val id = GroupReqListResult.sender_id
                            val group_id = GroupReqListResult.group_id

                            groupInvite.sender_uid = id
                            groupInvite.group_id = group_id

                            getUserInfo(UserModel(id))

                            //getGroupInfo(GroupInfoModel(group_id))

                        }
                    }
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<GroupReqListResult>>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }
        })
    }


    private fun getUserInfo(userModel: UserModel){
        val api=RetroInterface.create()
        api.user(userModel).enqueue(object : Callback<UserResult> {
            override fun onResponse(call: Call<UserResult>, response: Response<UserResult>) {
                if(response.isSuccessful){
                    val userresult = response.body()

                    if(userresult != null)
                    {
                        groupInvite.sender_name = userresult.name
                        groupInvite.sender_email = userresult.email

                        getGroupInfo(GroupInfoModel(groupInvite.group_id))
                    }
                }
            }

            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :asd", t.localizedMessage)

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
                        groupInvite.group_capacity = groupInfoList.capacity
                        groupInvite.group_date = groupInfoList.date
                        groupInvite.group_end = groupInfoList.end_time
                        groupInvite.group_start = groupInfoList.start_time
                        groupInvite.group_headcount = groupInfoList.headcount
                        groupInvite.group_location = groupInfoList.area
                        groupInvite.group_name = groupInfoList.name
                        groupInvite.group_score = groupInfoList.score

                        alarmGrouprequestAdapter.data.add(groupInvite)
                        alarmGrouprequestAdapter.notifyDataSetChanged()
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
}