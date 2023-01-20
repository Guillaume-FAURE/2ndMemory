package com.example.composeproject.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface BookAPI {
    @GET("https://www.googleapis.com/books/v1/volumes?key=AIzaSyBddni7j6_ryeuld8OKVdfozb7pe8dtXHo")
    fun searchBookByString(
        @Query("q") string: String,
    ): Call<ResponseBody>
}