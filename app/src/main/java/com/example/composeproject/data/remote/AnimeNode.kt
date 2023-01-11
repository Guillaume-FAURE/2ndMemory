package com.example.composeproject.data.remote


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