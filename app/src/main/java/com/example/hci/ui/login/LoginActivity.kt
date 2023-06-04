package com.example.hci.ui.login

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.example.hci.*

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
                val id = username.text.toString()
                val pw = password.text.toString()

                //이를 로그인 실행

                if(false) //Login Error
                {
                    //TODO 로그인 문제 생겼을 때 처리
                }
                else //Login OK
                {
                    Toast.makeText(this, "${id}님 환영합니다", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    MainActivity.flagLogin = true
                    intent.putExtra("id", id)
                    startActivity(intent)
                }
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
}