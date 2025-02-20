package com.example.myapplication.login

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RandomUserService {
    @GET
    fun getPictureByGender(
        @Url gender: String
    ):Call<RandomUserResponse>
}