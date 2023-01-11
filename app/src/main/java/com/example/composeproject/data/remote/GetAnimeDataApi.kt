package com.example.composeproject.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GetAnimeDataApi {

    @GET("https://api.myanimelist.net/v2/anime/")
    suspend fun getAnimeInfo(
        @Query("id") id: Int,
    ): AnimeDataDto
}