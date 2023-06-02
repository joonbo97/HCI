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

        val ImageIcon :ImageView = findViewById(R.id.icon_image)
        val categoryIDX = intent.getIntExtra("categoryIDX", 0)
        when(categoryIDX) {
            0 ->ImageIcon.setImageResource(R.drawable.category_health)
            1 ->ImageIcon.setImageResource(R.drawable.category_music)
            2 ->ImageIcon.setImageResource(R.drawable.category_travel)
            3 ->ImageIcon.setImageResource(R.drawable.category_study)
            4 ->ImageIcon.setImageResource(R.drawable.category_book)
            5 ->ImageIcon.setImageResource(R.drawable.category_language)
            6 ->ImageIcon.setImageResource(R.drawable.category_culcture)
            7 ->ImageIcon.setImageResource(R.drawable.category_game)
            8 ->ImageIcon.setImageResource(R.drawable.category_volunteer)
            9 ->ImageIcon.setImageResource(R.drawable.category_pet)
            10 ->ImageIcon.setImageResource(R.drawable.category_photo)
            11 ->ImageIcon.setImageResource(R.drawable.category_drive)
            else ->ImageIcon.setImageResource(R.drawable.category_health)
        }
    }
}