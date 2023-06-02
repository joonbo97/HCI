package com.example.hci

import com.example.hci.data.model.RegisterModel
import com.example.hci.data.model.RegisterResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException

interface RetroInterface {

    companion object {
        private const val BASE_URL = "http://ec2-54-180-114-74.ap-northeast-2.compute.amazonaws.com:3000/";

        fun create():RetroInterface{
            val gson : Gson = GsonBuilder().setLenient().create();

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideOkHttpClient(AppInterceptor()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetroInterface::class.java);
        }

        fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient
                = OkHttpClient.Builder().run {
            addInterceptor(interceptor)
            build()
        }

        class AppInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("Connection", "close")
                    .build()
                proceed(newRequest)
            }
        }
    }

    @POST("/register")
    fun register(
        @Body jsonparams: RegisterModel
    ) : Call<RegisterResult>

}