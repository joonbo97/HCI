package com.example.hci

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import com.example.hci.R
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

        val done :ImageView = findViewById(R.id.edit_btn2)
        done.setOnClickListener{
            //TODO : 변경완료버튼 눌렀을 때의 Action 수행
            //TODO : 완료 누르면 이전 Activity로 돌아감
            //TODO : 변경 되는 사진은 myphotoimage1 myphotoimage2 는 카메라버튼

            finish()
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
}