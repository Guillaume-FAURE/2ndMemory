package com.example.composeproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "art")
data class ArtEntity(
    @PrimaryKey(autoGenerate = true) val artId: Int? = null,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "mark") val mark: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "state") val state: String?,
    @ColumnInfo(name = "createdDate") val createdDate:String? = getCreatedDate(),
) {
    companion object {

        fun getCreatedDate(): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:MM:SS")
            return  LocalDateTime.now().format(formatter)
        }
    }
}