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

class AlarmGroupinviteFragment : Fragment() {
    lateinit var alarmgroupinviteadapter :AlarmGroupinviteAdapter
    val data = mutableListOf<GroupInvite>()
    var temp :Int = 0
    val groupInvite = GroupInvite(0,"", "", 0,"", "", "", "", "", 0, 0,0.0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_alarm_groupinvite, container, false)

        val recyclerView : RecyclerView = root.findViewById(R.id.alarm_group_invite_recycler)
        alarmgroupinviteadapter = AlarmGroupinviteAdapter(requireContext())
        recyclerView.adapter = alarmgroupinviteadapter

        getInviteGroupList(GroupInviteListModel(MainActivity.uid))

        alarmgroupinviteadapter.data.addAll(data)
        alarmgroupinviteadapter.notifyDataSetChanged()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getInviteGroupList(groupInviteListModel : GroupInviteListModel){
        val api= RetroInterface.create()
        api.groupInviteList(groupInviteListModel).enqueue(object : Callback<List<GroupInviteListResult>> {
            override fun onResponse(call: Call<List<GroupInviteListResult>>,response: Response<List<GroupInviteListResult>>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())
                    val groupinviterequest = response.body()

                    groupinviterequest?.let {
                        for(GroupInviteReqListResult in groupinviterequest)
                        {
                            val id = GroupInviteReqListResult.sender_id
                            val group_id = GroupInviteReqListResult.group_id
                            temp = group_id
                            groupInvite.sender_uid = id
                            groupInvite.group_id = temp
                            getUserInfo(UserModel(id))


                        }
                    }
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<GroupInviteListResult>>, t: Throwable) {
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

                        getGroupInfo(GroupInfoModel(temp))

                        alarmgroupinviteadapter.data.add(groupInvite)

                        alarmgroupinviteadapter.notifyDataSetChanged()
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