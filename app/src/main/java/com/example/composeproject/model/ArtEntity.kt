package com.example.composeproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "art")
data class ArtEntity(
    @PrimaryKey(autoGenerate = true) var artId: Int? = null,
    @ColumnInfo(name = "author") var author: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "mark") var mark: String?,
    @ColumnInfo(name = "type") var type: String?,
    @ColumnInfo(name = "picture") var picture: String?,
    @ColumnInfo(name = "state") var state: String?,
    @ColumnInfo(name = "createdDate") var createdDate:String? = getCreatedDate(),
) {
    companion object {

        fun getCreatedDate(): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:MM:SS")
            return  LocalDateTime.now().format(formatter)
        }
    }
}