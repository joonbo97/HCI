package com.example.hci

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

class MakegroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makegroup)

        val image1: ImageView = findViewById(R.id.back_image)

        image1.setOnClickListener {
            finish()
        }
    }
}