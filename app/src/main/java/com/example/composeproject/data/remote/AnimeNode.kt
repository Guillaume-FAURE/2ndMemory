package com.example.composeproject.data.remote

import com.squareup.moshi.Json


data class AnimeNode(
    val node : AnimeData
)

data class AnimeData(
    val id: Int,
    val title: String,
    val main_picture: PictureData
)

data class PictureData(
    val medium: String,
    val large: String
)

data class Genre(
    val id: Int,
    val name: String,
)

data class Studio(
    val id: Int,
    val name: String,
)

data class AnimeInformation(
    val id: Int,
    val title: String,
    val main_picture: PictureData,
    val start_date: String,
    val end_date: String,
    val synopsis: String,
    val mean: Float,
    val status: String,
    val num_episodes: Int,
    val genres: List<Genre>,
    val studios: List<Studio>,
)

data class ArtDataDto(
    val id: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "pictureURL")
    val pictureURL: String
)
