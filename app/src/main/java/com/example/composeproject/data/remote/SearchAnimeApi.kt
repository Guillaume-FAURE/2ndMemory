package com.example.composeproject.data.remote

import android.util.Log
import androidx.compose.runtime.MutableState
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface SearchAnimeApi {
    // Connection to my MyAnimeList.net
    @Headers(
        "Accept: application/json",
        "X-MAL-CLIENT-ID: 835b5176f5cc401297d2f3bff5c8e3dc"
    )

    @GET("https://api.myanimelist.net/v2/anime?limit=20")
    fun getAnimeId(
        @Query("q") string: String,
    ): Call<ResponseBody>
}