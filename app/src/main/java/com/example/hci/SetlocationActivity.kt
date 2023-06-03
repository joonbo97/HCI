package com.example.hci

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.hci.data.model.Setlocation.SetlocationModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SetlocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setlocation)

        val imageback : ImageView = findViewById(R.id.back_image)
        imageback.setOnClickListener {
            finish()
        }

        val spinnerCity : Spinner = findViewById(R.id.spinnerCity)
        val spinnerCity2 : Spinner = findViewById(R.id.spinnerCity2)
        spinnerCity.adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_dropdown_item)

        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val adapterResourceID = when(position)
                {
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

                spinnerCity2.adapter = ArrayAdapter.createFromResource(this@SetlocationActivity, adapterResourceID, android.R.layout.simple_spinner_dropdown_item)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        val doneimg :ImageButton = findViewById(R.id.nextbtn)
        doneimg.setOnClickListener {
            val city :String = spinnerCity.selectedItem.toString()
            val city2 :String = spinnerCity2.selectedItem.toString()
            var uid = intent.getStringExtra("uid")
            if(uid == null)
                uid = "Error"

            //SetLocation(SetlocationModel(city, city2, uid))
            SetLocation2(SetlocationModel(city, city2, uid))
        }


    }

    private fun SetLocation(setlocationModel :SetlocationModel)
    {
        val api = RetroInterface.create()
        api.setlocation(setlocationModel).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful()){
                    Log.d("Response: ", response.body().toString())
                    Toast.makeText(this@SetlocationActivity, "지역설정 완료.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@SetlocationActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(this@SetlocationActivity, "지역설정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@SetlocationActivity, "연결 실패. 인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show()
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }

        })

    }

    private fun SetLocation2(setlocationModel: SetlocationModel)
    {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-54-180-114-74.ap-northeast-2.compute.amazonaws.com:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retroInterface = retrofit.create(RetroInterface::class.java)

        val call = retroInterface.setlocation(setlocationModel)

        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val result = response.body() // 성공적인 응답 받았을 때 처리
                    Log.d("TAG", "Response: $result")
                    Toast.makeText(this@SetlocationActivity, "지역설정 완료.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("TAG", "Response error: ${response.code()}")
                    Toast.makeText(this@SetlocationActivity, "지역설정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("TAG", "Request failed: ${t.message}")
                Toast.makeText(this@SetlocationActivity, "연결 실패. 인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show()
            }

        })
    }
}