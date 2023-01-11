package com.example.composeproject.data.remote

import com.squareup.moshi.Json

data class AnimeDto(
    @field:Json(name = "node")
    val animeData: AnimeDataDto
)
