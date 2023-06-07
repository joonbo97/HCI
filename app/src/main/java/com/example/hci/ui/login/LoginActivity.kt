package com.example.hci.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.*
import com.example.hci.*
import com.example.hci.data.model.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<ImageButton>(R.id.login)

        username.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO
            }

            override fun afterTextChanged(p0: Editable?) {
                val inputText = p0.toString()
                if(inputText.length < 5)
                    username.error = "5글자 이상 입력해주세요."
                else if(inputText.length > 10)
                    username.error = "10글자 이하로 입력해주세요."
            }

        })

        password.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                val inputText = p0.toString()
                if(inputText.length < 5)
                    password.error = "5글자 이상 입력해주세요."
                else if(inputText.length > 10)
                    password.error = "10글자 이하로 입력해주세요."
            }

        })

        login.setOnClickListener{
            if(username.length() < 5 || username.length() > 10) //id의 형식 맞지않음
            {
                Toast.makeText(this, "아이디는 5~10글자입니다.", Toast.LENGTH_SHORT).show()
            }
            else if(password.length() < 5 || password.length() > 10)//pw의 형식 맞지않음
            {
                Toast.makeText(this, "비밀번호는 5~10글자입니다.", Toast.LENGTH_SHORT).show()
            }
            else//형식 문제 없음
            {
                val id :String = username.text.toString()
                val pw :String = password.text.toString()

                //이를 통해 로그인 실행

                login(LoginModel(id, pw))
            }
        }

        val ImageSearchIDPW: ImageView = findViewById(R.id.SearchIDPW)
        ImageSearchIDPW.setOnClickListener {
            val intent = Intent(this, SearchIDPW::class.java)
            startActivity(intent)
        }

        val ImageRegester: ImageView = findViewById(R.id.Regester)
        ImageRegester.setOnClickListener {
            val intent = Intent(this, RegesterActivity::class.java)
            startActivity(intent)
        }
    }

    //로그인 요청
    private fun login(loginModel : LoginModel){
        val api=RetroInterface.create()
        api.login(loginModel).enqueue(object : Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>,response: Response<LoginResult>) {
                if(response.isSuccessful()){
                    val loginresult = response.body()
                    MainActivity.uid = loginresult!!.uid


                    if(MainActivity.uid == -1)
                        Toast.makeText(this@LoginActivity, "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    else {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        MainActivity.flagLogin = true


                        //MainActivity.uid = response.body().toString().toInt()

                        //유저정보 받아옴
                        getUserInfo(UserModel(MainActivity.uid))

                        notify(NotificationModel(MainActivity.uid))

                        startActivity(intent)
                        Toast.makeText(this@LoginActivity, "${loginModel.id}님 환영합니다", Toast.LENGTH_SHORT).show()
                        finish()
                    }


                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(this@LoginActivity, "연결 실패. 인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(this@LoginActivity, "연결 실패. 인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun getUserInfo(userModel: UserModel){
        val api=RetroInterface.create()
        api.user(userModel).enqueue(object : Callback<UserResult> {
            override fun onResponse(call: Call<UserResult>,response: Response<UserResult>) {
                if(response.isSuccessful){
                    val userresult = response.body()

                    MainActivity.name =userresult!!.name
                    MainActivity.location_id = userresult.location_id
                    MainActivity.email = userresult.email
                    MainActivity.score = userresult.score

                    MainActivity.city = MainActivity().LocationIDSearch(MainActivity.location_id)[0].toString()
                    MainActivity.district = MainActivity().LocationIDSearch(MainActivity.location_id)[1].toString()
                }
                else
                {
                    Toast.makeText(this@LoginActivity, "유저 정보 갱신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)

            }
        })
    }

    private fun notify(notificationModel : NotificationModel){
        val api=RetroInterface.create()
        api.notification(notificationModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    MainActivity.flagAlarm = response.body() != "false"
                }
                else
                {

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }
        })
    }
}