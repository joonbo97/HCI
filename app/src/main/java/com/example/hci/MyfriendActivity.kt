package com.example.hci

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.hci.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyfriendActivity : AppCompatActivity() {
    lateinit var myfriendAdapter: MyfriendAdapter
    val data = mutableListOf<MyfriendData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfriend)

        val image1: ImageView = findViewById(R.id.back_image)
        image1.setOnClickListener {
            finish()
        }

        val addfriendbtn :ImageView = findViewById(R.id.addfriend_img)

        //친구추가 버튼 클릭
        addfriendbtn.setOnClickListener {
            val dialog = AddFriendDialog(this)
            dialog.showDialog()
        }

        initRecycler()

        myfriendAdapter.notifyDataSetChanged()

    }

    private fun initRecycler() {
        val recyclerView: RecyclerView = findViewById(R.id.Myfriend_RecyclerView)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        myfriendAdapter = MyfriendAdapter(this)
        recyclerView.adapter = myfriendAdapter


        getFriendList(FriendListModel(MainActivity.uid))


        myfriendAdapter.data.addAll(data)
        myfriendAdapter.notifyDataSetChanged()//변경감지
    }


    private fun getFriendList(friendListModel : FriendListModel){
        val api=RetroInterface.create()
        api.friendList(friendListModel).enqueue(object : Callback<List<FriendListResult>> {
            override fun onResponse(call: Call<List<FriendListResult>>,response: Response<List<FriendListResult>>) {
                if(response.isSuccessful()){
                    Log.d("Response:", response.body().toString())

                    val friendIds = response.body()
                    friendIds?.let {
                        for(FriendListResult in friendIds)
                        {


                            val id = FriendListResult.friend_id
                            getUserInfo(UserModel(id))


                        }
                    }

                    myfriendAdapter.data.addAll(data)
                    myfriendAdapter.notifyDataSetChanged()

                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<FriendListResult>>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }
        })
    }

    private fun getUserInfo(userModel: UserModel){
        val api=RetroInterface.create()
        api.user(userModel).enqueue(object : Callback<UserResult> {
            override fun onResponse(call: Call<UserResult>, response: Response<UserResult>) {
                if(response.isSuccessful){
                    myfriendAdapter.data.add(MyfriendData(userModel.uid, response.body()!!.name, response.body()!!.email))
                    myfriendAdapter.notifyDataSetChanged()
                }
                else
                {

                }
            }

            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)

            }
        })
    }
}