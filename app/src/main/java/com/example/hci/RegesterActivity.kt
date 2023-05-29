package com.example.hci

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView

class RegesterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regester)

        val imageback : ImageView = findViewById(R.id.back_image)
        imageback.setOnClickListener {
            finish()
        }

        val nextBtn: ImageButton = findViewById(R.id.nextbtn)
        nextBtn.setOnClickListener {
            val intent = Intent(this, SetlocationActivity::class.java)
            startActivity(intent)
        }
    }
}