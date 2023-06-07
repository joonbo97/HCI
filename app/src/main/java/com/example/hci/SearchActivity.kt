package com.example.hci

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val image1: ImageView = findViewById(R.id.back_image3)
        image1.setOnClickListener {
            finish()
        }
    }
}