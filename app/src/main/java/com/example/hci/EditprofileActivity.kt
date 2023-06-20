package com.example.hci

import android.app.Activity
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
import android.widget.TextView
import android.widget.Toast
import com.example.hci.data.model.ProfileImg.ProfileImgModel
import com.example.hci.data.model.ProfileImg.ProfileImgResult
import com.example.hci.data.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.hci.data.model.UserModifyModel
import com.example.hci.data.model.UserResult
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream

class EditprofileActivity : AppCompatActivity() {
    lateinit var profileImageView :CircleImageView
    var imgStr : String? = null

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


        val locationText :TextView = findViewById(R.id.mylocation_text)
        locationText.text = MainActivity.city + " " + MainActivity.district
        locationText.setOnClickListener {
            Toast.makeText(this, "지역설정 창으로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SetlocationActivity::class.java)

            intent.putExtra("city", MainActivity.city)
            intent.putExtra("district", MainActivity.district)


            startActivity(intent)
        }


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
            profileImageView = findViewById(R.id.civ_profile)
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {//카메라
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
                try {
                    var bm = resize(imageBitmap)
                    var imageStr = bitmapToByteArray(bm)
                    postImgToDB(ProfileImgModel(MainActivity.uid, imageStr))
                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                }
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


    private fun postImgToDB(profileImgModel: ProfileImgModel){
        val api=RetroInterface.create()
        api.profileImgPost(profileImgModel).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>,response: Response<String>) {
                if(response.isSuccessful()){
                    if(response.body() == "success") {
                        Toast.makeText(this@EditprofileActivity, "프로필 사진 수정을 완료했습니다.", Toast.LENGTH_SHORT).show()
                        //getUserInfo(UserModel(MainActivity.uid))
                        finish()
                    }
                    else
                        Toast.makeText(this@EditprofileActivity, "프로필 사진 수정에 실패했습니다. 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("Response FAILURE", response.body().toString())
                    Toast.makeText(this@EditprofileActivity, "프로필 사진 수정에 실패했습니다. 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("CONNECTION FAILURE :", t.localizedMessage)
                Toast.makeText(this@EditprofileActivity, "프로필 사진 수정에 실패했습니다. 인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }

// 비트맵이미지를 blob으로 바꿔줌
    private fun bitmapToByteArray(bitmap:Bitmap):String{
        var image = ""
        var stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
        var byteArray = stream.toByteArray()
        image = "&image="+byteArrayToBinaryString(byteArray)
        return image
    }

    private fun byteArrayToBinaryString(b: ByteArray):String{
        var sb = java.lang.StringBuilder()
        for(i in 0..b.size){
            sb.append(byteToBinaryString(b[i]))
        }
        return sb.toString()
    }

    private fun byteToBinaryString(n:Byte):String{
        var sb = java.lang.StringBuilder("00000000")
        for(bit in 0..7){
            if(((n.toInt() shr bit) and 1)>0){
                sb.setCharAt(7-bit,'1')
            }
        }
        return sb.toString()
    }

    private fun resize(bm : Bitmap):Bitmap{
        var bm2 = bm
        var config = resources.configuration
        if(config.smallestScreenWidthDp>=800)
            bm2 = Bitmap.createScaledBitmap(bm2,400,240,true)
        else if(config.smallestScreenWidthDp>=600)
            bm2 = Bitmap.createScaledBitmap(bm2,300,180,true)
        else if(config.smallestScreenWidthDp>=400)
            bm2 = Bitmap.createScaledBitmap(bm2,200,120,true)
        else if(config.smallestScreenWidthDp>=360)
            bm2 = Bitmap.createScaledBitmap(bm2,180,108,true)
        else
            bm2 = Bitmap.createScaledBitmap(bm2,160,96,true)
        return bm2
    }


//    private fun StringToBitmap(imgStr:String):Bitmap{
//
//        return null
//    }
//
//    private fun getImgFromDB(userModel: UserModel):String{
//        val api=RetroInterface.create()
//        api.profileImgGet(userModel).enqueue(object : Callback<ProfileImgResult> {
//            override fun onResponse(call: Call<ProfileImgResult>,response: Response<ProfileImgResult>) {
//                if(response.isSuccessful){
//                    val result = response.body()
//
//                    var imgBlob = result!!.image
//                    imgStr = imgBlob
//                }
//                else
//                {
//
//                }
//            }
//
//            override fun onFailure(call: Call<ProfileImgResult>, t: Throwable) {
//                Log.d("CONNECTION FAILURE :", t.localizedMessage)
//
//            }
//        })
//    }

}