package com.example.hci

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.hci.data.model.Setlocation.SetlocationModel
import com.example.hci.data.model.UserModel
import com.example.hci.data.model.UserResult
import com.example.hci.ui.home.HomeFragment
import com.example.hci.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SetlocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setlocation)

        val spinnerCity: Spinner = findViewById(R.id.spinnerCity)
        val spinnerCity2: Spinner = findViewById(R.id.spinnerCity2)
        spinnerCity.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.cities,
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val adapterResourceID = when (position) {
                    0 -> R.array.city0
                    1 -> R.array.city1
                    2 -> R.array.city2
                    3 -> R.array.city3
                    4 -> R.array.city4
                    5 -> R.array.city5
                    6 -> R.array.city6
                    7 -> R.array.city7
                    8 -> R.array.city8
                    9 -> R.array.city9
                    10 -> R.array.city10
                    11 -> R.array.city11
                    12 -> R.array.city12
                    13 -> R.array.city13
                    14 -> R.array.city14
                    15 -> R.array.city15
                    16 -> R.array.city16
                    else -> R.array.city0
                }

                spinnerCity2.adapter = ArrayAdapter.createFromResource(
                    this@SetlocationActivity,
                    adapterResourceID,
                    android.R.layout.simple_spinner_dropdown_item
                )

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val imageback: ImageView = findViewById(R.id.back_image)
        imageback.setOnClickListener {
            finish()
        }

        //스피너, 뒤로가기 버튼 세팅완료


        if (MainActivity.flagLogin) //로그인 되어있으면 수정모드
        {
            val title :TextView = findViewById(R.id.setlocation_text)
            title.text = "내 지역 수정"

            val btn :ImageButton = findViewById(R.id.nextbtn)
            btn.setImageResource(R.drawable.editlocation_done)

            btn.setOnClickListener{
                val city: String = spinnerCity.selectedItem.toString()
                val district: String = spinnerCity2.selectedItem.toString()

                SetLocation(SetlocationModel(city, district, MainActivity.uid))

                finish()
            }
        }
        else
        {
            val doneimg: ImageButton = findViewById(R.id.nextbtn)
            doneimg.setOnClickListener {
                val city: String = spinnerCity.selectedItem.toString()
                val district: String = spinnerCity2.selectedItem.toString()
                val uid = intent.getIntExtra("uid", -1)
                if (uid == -1) {
                    Toast.makeText(this, "회원가입에 실패했습니다. 처음부터 시도해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    SetLocation(SetlocationModel(city, district, uid))
                }
            }
        }
    }

    private fun getUserInfo(userModel: UserModel){
        val api=RetroInterface.create()
        api.user(userModel).enqueue(object : Callback<UserResult> {
            override fun onResponse(call: Call<UserResult>, response: Response<UserResult>) {
                if(response.isSuccessful){
                    val userresult = response.body()

                    MainActivity.name =userresult!!.name
                    MainActivity.location_id = userresult.location_id
                    MainActivity.email = userresult.email
                    MainActivity.score = userresult.score
                    MainActivity.city = MainActivity().LocationIDSearch(MainActivity.location_id)[0].toString()
                    MainActivity.district = MainActivity().LocationIDSearch(MainActivity.location_id)[1].toString()
                }
            }

            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)

            }
        })
    }

    private fun SetLocation(setlocationModel :SetlocationModel)
    {
        val api = RetroInterface.create()
        api.setlocation(setlocationModel).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful)
                {
                    Toast.makeText(this@SetlocationActivity, "${setlocationModel.city} ${setlocationModel.district}로 지역설정 완료하였습니다.", Toast.LENGTH_SHORT).show()

                    if(!MainActivity.flagLogin) {
                        val intent = Intent(this@SetlocationActivity, MainActivity::class.java)
                        intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    else
                        getUserInfo(UserModel(MainActivity.uid))
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(this@SetlocationActivity, "지역설정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@SetlocationActivity, "연결 실패. 인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show()
            }

        })

    }
}