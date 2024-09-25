package com.example.iorms

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiClient {

    fun retrofitClient(): Retrofit{
        return Retrofit.Builder().baseUrl("http://103.169.83.174:8081")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun providesApiService(): ApiService{
        return retrofitClient().create(ApiService::class.java)
     }
}