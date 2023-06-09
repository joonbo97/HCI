package com.example.hci

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.example.hci.data.model.AuthMatchModel
import com.example.hci.data.model.AuthMatchResult
import com.example.hci.data.model.AuthSendEmailModel
import com.example.hci.data.model.AuthSendEmailResult
import com.example.hci.data.model.Register.RegisterModel
import com.example.hci.data.model.Register.RegisterResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class RegesterActivity : AppCompatActivity() {
    var emailflag = false
    var senddoneflag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regester)

        val imageback : ImageView = findViewById(R.id.back_image)
        imageback.setOnClickListener {
            finish()
        }

        //회원가입 값 받아오기
        var id :String
        var pw :String = ""
        var pw2 :String = ""
        var name :String
        var email :String
        var emailcode :Int

        val editID :EditText = findViewById(R.id.editID)
        val editPW :EditText = findViewById(R.id.editPW)
        val editPW2 :EditText = findViewById(R.id.editPW2)
        val editName :EditText = findViewById(R.id.editName)
        val editEmail :EditText = findViewById(R.id.editEmail)
        val editEmailCode :EditText = findViewById(R.id.editEmailCode)

        val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        fun checkid() :Boolean //ID입력이 되었는지 check
        {
            return editID.length() == 0
        }
        fun checkpw() :Boolean //PW입력이 되었는지 check
        {
            return editPW.length() == 0
        }
        fun checkpw2() :Boolean //PW확인 입력이 되었는지 check
        {
            return editPW2.length() == 0
        }
        fun checkname() :Boolean //email입력이 되었는지 check
        {
            return editName.length() == 0
        }
        fun checkemail() :Boolean //email입력이 되었는지 check
        {
            return editEmail.length() == 0
        }
        fun checkemailcode() :Boolean //ID입력이 되었는지 check
        {
            return editEmailCode.length() == 0
        }
        fun emailvalidation() :Boolean
        {
            email = editEmail.text.toString().trim()
            val pattern = Pattern.matches(emailValidation, email)
            return !pattern
        }
        fun CheckEditTextIsEmpth() :Int //빈칸이 있는지 전체 check. 빈칸이 있으면 1~6까지 숫자, 없으면 0
        {
            if(checkid())
            {
                Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                return 1
            }
            else
            {
                if(editID.length() < 5 || editID.length() > 10)
                {
                    Toast.makeText(this, "아이디는 5~10글자 이내 입니다.", Toast.LENGTH_SHORT).show()
                    return 1
                }
                else if(checkpw())
                {
                    Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    return 2
                }
                else
                {
                    if(editPW.length() < 5 || editPW.length() > 10)
                    {
                        Toast.makeText(this, "비밀번호는 5~10글자 이내 입니다.", Toast.LENGTH_SHORT).show()
                        return 2
                    }
                    else if(checkpw2())
                    {
                        Toast.makeText(this, "비빌번호확인을 입력해주세요", Toast.LENGTH_SHORT).show()
                        return 3
                    }
                    else
                    {
                        if (pw != pw2)
                        {
                            Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지않습니다.", Toast.LENGTH_SHORT).show()
                            return 3
                        }
                        else
                        {
                            if(checkname())
                            {
                                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                                return 4
                            }
                            else
                            {
                                if(checkemail())
                                {
                                    Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
                                    return 5
                                }
                                else
                                {
                                    if(emailvalidation())
                                    {
                                        Toast.makeText(this, "이메일형식이 아닙니다.", Toast.LENGTH_SHORT).show()
                                        return 5
                                    }
                                    else if(checkemailcode())
                                    {
                                        Toast.makeText(this, "이메일로 전송된 코드를 입력해주세요", Toast.LENGTH_SHORT).show()
                                        return 6
                                    }
                                    else
                                        return 0
                                }
                            }
                        }
                    }
                }
            }
        }



        val sendbtn :ImageView = findViewById(R.id.inboxsend)
        sendbtn.setOnClickListener {
            if(!emailvalidation() && !checkemail())
            {
                //버튼 비활성화
                sendbtn.isEnabled = false
                sendbtn.setImageResource(R.drawable.inboxok2)

                Handler(Looper.getMainLooper()).postDelayed({
                    sendbtn.isEnabled = true
                    sendbtn.setImageResource(R.drawable.regester_sendbtn)
                }, 3000)

                email = editEmail.text.toString()
                SendEmailCode(AuthSendEmailModel(email))
                senddoneflag = true



                //Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null)
            }
            else
            {
                Toast.makeText(this, "이메일 형식을 입력해주세요.", Toast.LENGTH_SHORT).show()
                senddoneflag = false
            }
        }

        val okbtn :ImageView = findViewById(R.id.inboxok)
        okbtn.setOnClickListener {
            if(checkemail()) //비었으면
            {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(checkemailcode())
            {
                Toast.makeText(this, "이메일인증 코드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(!senddoneflag)
            {
                Toast.makeText(this, "이메일인증을 진행해주세요.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                emailcode = editEmailCode.text.toString().toInt()
                email = editEmail.text.toString()
                checkEmailCode(AuthMatchModel(emailcode, email))
            }
        }


        val nextBtn: ImageButton = findViewById(R.id.nextbtn)
        nextBtn.setOnClickListener {
            pw = editPW.text.toString()
            pw2 = editPW2.text.toString()
            if(CheckEditTextIsEmpth()!=0)// 빈칸, 아이디 비밀 번호 이메일 형식 검사
            {
                //TODO() //각 항목별 문제가 있을 때
            }
            else if(!emailflag)
            {
                Toast.makeText(this, "이메일 인증을 완료해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {

                id = editID.text.toString()
                name = editName.text.toString()
                email = editEmail.text.toString()

                Register(RegisterModel(id, pw, name, email))
            }
        }
    }

    private fun SendEmailCode(authSendEmailModel : AuthSendEmailModel){
        val api = RetroInterface.create()
        api.authSendEmail(authSendEmailModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                //response.body() = 인증번호를 보냈습니다.
                if(response.isSuccessful){
                    Toast.makeText(this@RegesterActivity, "메일 발송 완료.", Toast.LENGTH_SHORT).show() //인증번호를 보냈습니다.
                }
                else
                {
                    Toast.makeText(this@RegesterActivity, "메일 발송 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@RegesterActivity, "메일 발송 실패. 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                Log.d("ErrorMessage : ", t.localizedMessage)
            }
        })
    }

    private fun checkEmailCode(authMatchModel : AuthMatchModel){
        val api=RetroInterface.create()
        api.authMatch(authMatchModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful){
                    val message :String = response.body().toString()

                    if(message == "인증번호 일치")
                    {
                        Toast.makeText(this@RegesterActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
                        emailflag = true
                    }
                    else
                    {
                        Toast.makeText(this@RegesterActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
                        emailflag = false
                    }
                }
                else
                {
                    Toast.makeText(this@RegesterActivity, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    emailflag = false
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@RegesterActivity, "연결에 실패했습니다. 인터넷을 확인해주세요.", Toast.LENGTH_SHORT).show()
                emailflag = false
            }
        })
    }

    private fun Register(registerModel : RegisterModel){
        val api=RetroInterface.create()
        api.register(registerModel).enqueue(object : Callback<RegisterResult> {
            override fun onResponse(call: Call<RegisterResult>,response: Response<RegisterResult>) {
                if(response.isSuccessful){
                    val registerresult = response.body()
                    val uid : Int = registerresult!!.uid
                    if((uid != -1 ) && senddoneflag && emailflag)
                    {
                        Toast.makeText(
                            this@RegesterActivity, "회원가입 성공 지역을 선택해주세요.", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@RegesterActivity, SetlocationActivity::class.java)
                        intent.putExtra("uid", uid)
                        startActivity(intent)
                    }
                    else if(!senddoneflag || !emailflag)
                    {
                        Toast.makeText(this@RegesterActivity, "이메일 인증이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(this@RegesterActivity, "ID가 중복되었습니다. 다른 ID로 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(this@RegesterActivity, "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResult>, t: Throwable) {
                Toast.makeText(this@RegesterActivity, "연결 실패. 인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show()
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
            }
        })
    }
}