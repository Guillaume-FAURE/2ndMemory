package com.example.composeproject

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class ArtModel constructor(
    val id: String = getUuid(),
    val author: String,
    val title: String,
    val imageURL: Painter,
    val description: String,
    val mark: String,
    val type: String,
    val state: String,
    val createdDate:String = getCreatedDate(),
) {
    companion object {
        fun getUuid(): String {
            return UUID.randomUUID().toString()
        }

        fun getCreatedDate(): String {
            val formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM:SS")
            return  LocalDateTime.now().format(formatter)
        }
    }
}