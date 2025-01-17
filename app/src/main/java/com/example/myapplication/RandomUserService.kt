package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface RandomUserService {
    @GET("api/")
    fun getRandomPicture(): Call<PictureResponse>
}