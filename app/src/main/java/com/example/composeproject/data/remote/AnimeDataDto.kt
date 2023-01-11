package com.example.composeproject.data.remote

import com.squareup.moshi.Json


data class AnimeDataDto(
    val id: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "pictureURL")
    val pictureURL: String
)
