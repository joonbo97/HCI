package com.example.hci

import com.example.hci.data.model.RegisterModel
import com.example.hci.data.model.RegisterResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

interface RetroInterface {

    companion object {
        private const val BASE_URL = "http://ec2-54-180-114-74.ap-northeast-2.compute.amazonaws.com:3000/";

        fun create():RetroInterface{
            val gson : Gson = GsonBuilder().setLenient().create();

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroInterface::class.java);
        }
    }

    @POST("/register")
    fun register(
        @Body jsonparms : RegisterModel
    ) : Call<RegisterResult>



}