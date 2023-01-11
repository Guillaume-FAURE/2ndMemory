package com.example.composeproject.data.remote

import com.squareup.moshi.Json

data class MangaNode(
    val node : MangaData
)

data class MangaData(
    val id: Int,
    val title: String,
    val main_picture: PictureData
)

data class Author(
    val node: AuthorData,
    val role: String,
)

data class AuthorData(
    val id: Int,
    val first_name: String,
    val last_name: String,
)

data class MangaInformation(
    val id: Int,
    val title: String,
    val main_picture: PictureData,
    val start_date: String,
    val end_date: String,
    val synopsis: String,
    val mean: Float,
    val status: String,
    val num_chapters: Int,
    val genres: List<Genre>,
    val authors: List<Author>,
)
