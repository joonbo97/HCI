package com.example.hci

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.hci.data.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.hci.data.model.UserModifyModel
import com.example.hci.data.model.UserResult
import java.io.FileNotFoundException
import java.io.InputStream

class EditprofileActivity : AppCompatActivity() {
    lateinit var profileImageView :ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        val image1: ImageView = findViewById(R.id.back_image4)
        image1.setOnClickListener {
            finish()
        }

        val nameText :EditText = findViewById(R.id.myname_text)
       // val locationText :EditText = findViewById(R.id.mylocation_text)
        val emailText :EditText = findViewById(R.id.myemail_text)



        if (MainActivity.name.isEmpty()) {
            nameText.isEnabled = true
            nameText.hint = "이름을 입력해주세요"
        } else {
            nameText.hint = MainActivity.name
        }
       // locationText.hint = MainActivity.city + " " +MainActivity.district
        if (MainActivity.email.isEmpty()) {
            emailText.isEnabled = true
            emailText.hint = "이메일을 입력해주세요"
        } else {
            emailText.hint = MainActivity.email
        }


        val done :ImageView = findViewById(R.id.edit_btn2)
        done.setOnClickListener{
            //TODO : 변경완료버튼 눌렀을 때의 Action 수행
            //TODO : 완료 누르면 이전 Activity로 돌아감
            //TODO : 변경 되는 사진은 myphotoimage1 myphotoimage2 는 카메라버튼

            val newname :String
            val newemail :String

            if(nameText.text.isEmpty())
                newname = MainActivity.name
            else
                newname = nameText.text.toString()

            if(emailText.text.isEmpty())
                newemail = MainActivity.email
            else
                newemail = emailText.text.toString()

            modifyUserInfo(UserModifyModel(MainActivity.uid, newname, newemail))
        }

        val camera :ImageView = findViewById(R.id.myphoto_image2)
        camera.setOnClickListener {
            //TODO : 카메라버튼(프사변경)수행
            profileImageView = findViewById(R.id.myphoto_image)
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val selectedImage: Uri? = data?.data
            val imageBitmap: Bitmap? = selectedImage?.let { uri ->
                try {
                    val inputStream: InputStream? = contentResolver.openInputStream(uri)
                    BitmapFactory.decodeStream(inputStream)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    null
                }
            }

            if (imageBitmap != null) {
                profileImageView.setImageBitmap(imageBitmap)
            }
        }
    }

    private fun modifyUserInfo(userModifyModel : UserModifyModel){
        val api=RetroInterface.create()
        api.userModify(userModifyModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    if(response.body() == "success") {
                        Toast.makeText(this@EditprofileActivity, "정보수정을 완료했습니다.", Toast.LENGTH_SHORT).show()
                        getUserInfo(UserModel(MainActivity.uid))
                        finish()
                    }
                    else
                        Toast.makeText(this@EditprofileActivity, "정보수정에 실패했습니다. 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(this@EditprofileActivity, "정보수정에 실패했습니다. 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(this@EditprofileActivity, "정보수정에 실패했습니다. 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
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

                }
            }

            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)

            }
        })
    }
}