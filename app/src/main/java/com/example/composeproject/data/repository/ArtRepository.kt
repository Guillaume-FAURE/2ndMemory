package com.example.composeproject.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.composeproject.data.ArtDAO
import com.example.composeproject.model.ArtEntity

class ArtRepository(private val artDAO: ArtDAO) {
    fun getAll(): List<ArtEntity> = artDAO.getAll()
    fun loadAllByIds(artIds: IntArray): List<ArtEntity> = artDAO.loadAllByIds(artIds=artIds)
    fun findByAuthorTitle(author: String, title: String): ArtEntity = artDAO.findByAuthorTitle(author = author, title=title)
    fun insertAll(vararg arts: ArtEntity) = artDAO.insertAll(arts=arts)
    fun delete(art: ArtEntity) = artDAO.delete(art=art)
}