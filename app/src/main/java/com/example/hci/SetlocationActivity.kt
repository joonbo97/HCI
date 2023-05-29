package com.example.hci

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner

class SetlocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setlocation)

        val imageback : ImageView = findViewById(R.id.back_image)
        imageback.setOnClickListener {
            finish()
        }

        val spinnerCity : Spinner = findViewById(R.id.spinnerCity)
        val spinnerCity2 : Spinner = findViewById(R.id.spinnerCity2)
        spinnerCity.adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_dropdown_item)

        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val adapterResourceID = when(position)
                {
                    0 -> R.array.city0
                    1 -> R.array.city1
                    2 -> R.array.city2
                    3 -> R.array.city3
                    4 -> R.array.city4
                    5 -> R.array.city5
                    6 -> R.array.city6
                    7 -> R.array.city7
                    8 -> R.array.city8
                    9 -> R.array.city9
                    10 -> R.array.city10
                    11 -> R.array.city11
                    12 -> R.array.city12
                    13 -> R.array.city13
                    14 -> R.array.city14
                    15 -> R.array.city15
                    16 -> R.array.city16
                    else -> R.array.city0
                }

                spinnerCity2.adapter = ArrayAdapter.createFromResource(this@SetlocationActivity, adapterResourceID, android.R.layout.simple_spinner_dropdown_item)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}