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

class AlarmFriendFragment : Fragment() {
    lateinit var alarmfrandadapter :AlarmFrandAdapter
    val data = mutableListOf<FriendReqListResult2>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_alarm_friend, container, false)

        val recyclerView :RecyclerView = root.findViewById(R.id.alarm_friend_recycler)

        alarmfrandadapter = AlarmFrandAdapter(requireContext())
        recyclerView.adapter = alarmfrandadapter

        getFriendReq(FriendReqListModel(MainActivity.uid))


        alarmfrandadapter.data.addAll(data)
        alarmfrandadapter.notifyDataSetChanged()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getFriendReq(friendReqListModel : FriendReqListModel){
        val api= RetroInterface.create()
        api.friendReqList(friendReqListModel).enqueue(object : Callback<List<FriendReqListResult>> {
            override fun onResponse(call: Call<List<FriendReqListResult>>,response: Response<List<FriendReqListResult>>) {
                if(response.isSuccessful){
                    val friendrequserIDs = response.body()

                    friendrequserIDs?.let {
                        for(FriendReqListResult in friendrequserIDs)
                        {
                            val id = FriendReqListResult.from_id

                            getUserInfo(UserModel(id))


                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<FriendReqListResult>>, t: Throwable) {
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
                        alarmfrandadapter.data.add(FriendReqListResult2(userresult.id, userresult.name, userresult.email))
                        alarmfrandadapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :asd", t.localizedMessage)

            }
        })
    }
}