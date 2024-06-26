package com.example.vinilosmisw4203_2024.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ArtistRetrofitInstance {
    private const val BASE_RUL = "http://54.227.206.253:3000/"
    val api: ArtistService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtistService::class.java)
    }
}
