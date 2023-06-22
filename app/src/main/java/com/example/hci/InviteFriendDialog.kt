package com.example.hci

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import com.example.hci.data.model.*
import com.example.hci.ui.MyGroup.MyGroupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InviteFriendDialog (context: Context, val friend_id :Int) {
    private val dialog = Dialog(context, R.style.CustomDialog)
    private lateinit var onClickListener: AddFriendDialog.OnDialogClickListener
    private var context :Context = context
    lateinit var inviteFriendAdapter: InviteFriendAdapter
    val data = mutableListOf<InviteFriendData>()

    fun setOnClickListener(listener: AddFriendDialog.OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog()
    {
        dialog.setContentView(R.layout.invitegroup_layout)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val cancelbtn :ImageView = dialog.findViewById(R.id.cancel)


        initRecycler()

        inviteFriendAdapter.notifyDataSetChanged()

        cancelbtn.setOnClickListener {
            dialog.cancel()
        }

    }

    private fun initRecycler(){
        val recyclerView : RecyclerView = dialog.findViewById(R.id.recyclerView)

        inviteFriendAdapter = InviteFriendAdapter(context)
        recyclerView.adapter = inviteFriendAdapter

        getOperatedGroup(GroupCreatedModel(MainActivity.uid))


        inviteFriendAdapter.data.addAll(data)
        inviteFriendAdapter.notifyDataSetChanged()
    }

    private fun getOperatedGroup(groupCreatedModel : GroupCreatedModel){
        val api= RetroInterface.create()
        api.groupCreated(groupCreatedModel).enqueue(object : Callback<List<GroupCreatedResult>> {
            override fun onResponse(call: Call<List<GroupCreatedResult>>, response: Response<List<GroupCreatedResult>>) {
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


                            inviteFriendAdapter.data.add(
                                InviteFriendData(
                                    groupInfoList.name,
                                    groupInfoList.score,
                                    master_name,
                                    groupInfoList.headcount,
                                    groupInfoList.capacity,
                                    groupInfoList.description,
                                    groupInfoList.date,
                                    groupInfoList.start_time,
                                    groupInfoList.end_time,
                                    groupInfoList.area,
                                    groupInfoModel.group_id,
                                    friend_id
                                )
                            )
                            inviteFriendAdapter.notifyDataSetChanged()
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