package com.example.composeproject.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaAPI {
    // Connection to my MyAnimeList.net
    @Headers(
        "Accept: application/json",
        "X-MAL-CLIENT-ID: 835b5176f5cc401297d2f3bff5c8e3dc"
    )

    @GET("https://api.myanimelist.net/v2/manga?limit=20")
    fun searchMangaByString(
        @Query("q") string: String,
    ): Call<ResponseBody>

    @Headers(
        "Accept: application/json",
        "X-MAL-CLIENT-ID: 835b5176f5cc401297d2f3bff5c8e3dc"
    )

    @GET("https://api.myanimelist.net/v2/manga/{id}?fields=id,title,main_picture,start_date,end_date,synopsis,mean,status,genres,num_chapters,authors{first_name,last_name}")
    fun getMangaInfo(
        @Path("id") id: Int,
    ): Call<ResponseBody>
}