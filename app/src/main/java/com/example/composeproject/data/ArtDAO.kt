package com.example.composeproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.composeproject.model.ArtEntity

@Dao
interface ArtDAO {
    @Query("SELECT * FROM art")
    fun getAll(): List<ArtEntity>

    @Query("SELECT * FROM art WHERE artId IN (:artIds)")
    fun loadAllByIds(artIds: IntArray): List<ArtEntity>

    @Query("SELECT * FROM art WHERE author LIKE :author AND " +
            "title LIKE :title LIMIT 1")
    fun findByAuthorTitle(author: String, title: String): ArtEntity

    @Insert
    fun insertAll(vararg arts: ArtEntity)

    @Delete
    fun delete(art: ArtEntity)

}