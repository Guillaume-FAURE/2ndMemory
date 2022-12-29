package com.example.composeproject.data

import androidx.room.*
import com.example.composeproject.model.ArtEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtDAO {
    @Query("SELECT * FROM art")
    fun getAll(): Flow<List<ArtEntity>>
    @Insert
    suspend fun insert(art: ArtEntity)
    @Update
    suspend fun update(art: ArtEntity)
    @Delete
    suspend fun delete(art: ArtEntity)

}