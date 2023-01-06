package com.example.composeproject.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.composeproject.data.ArtDAO
import com.example.composeproject.model.ArtEntity
import kotlinx.coroutines.flow.Flow

class ArtRepository(
    private val artDAO: ArtDAO
) {
    fun getAll(): Flow<List<ArtEntity>> = artDAO.getAll()
    suspend fun insert(art: ArtEntity) = artDAO.insert(art = art)
    suspend fun update(art: ArtEntity) = artDAO.update(art = art)
    suspend fun delete(art: ArtEntity) = artDAO.delete(art = art)
}