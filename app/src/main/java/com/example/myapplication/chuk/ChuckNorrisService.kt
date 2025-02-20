package com.example.myapplication.chuk

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ChuckNorrisService {
    @GET
    fun getMemeByCategory(
        @Url category: String
    ): Call<Value>
}