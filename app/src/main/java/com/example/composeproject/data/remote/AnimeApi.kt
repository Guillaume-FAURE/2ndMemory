package com.example.composeproject.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AnimeAPI {
    // Connection to my MyAnimeList.net
    @Headers(
        "Accept: application/json",
        "X-MAL-CLIENT-ID: 835b5176f5cc401297d2f3bff5c8e3dc"
    )

    @GET("https://api.myanimelist.net/v2/anime?limit=20")
    fun searchAnimeByString(
        @Query("q") string: String,
    ): Call<ResponseBody>

    @Headers(
        "Accept: application/json",
        "X-MAL-CLIENT-ID: 835b5176f5cc401297d2f3bff5c8e3dc"
    )

    @GET("https://api.myanimelist.net/v2/anime/{id}?fields=id,title,main_picture,start_date,end_date,synopsis,mean,status,genres,num_episodes, studios")
    fun getAnimeInfo(
        @Path("id") id: Int,
    ): Call<ResponseBody>
}