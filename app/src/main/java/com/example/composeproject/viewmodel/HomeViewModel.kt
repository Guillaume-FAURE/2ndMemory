package com.example.composeproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composeproject.data.repository.ArtRepository
import com.example.composeproject.model.ArtEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelAbstract {
    val artList: Flow<List<ArtEntity>>
    fun addArt(art: ArtEntity)
    fun updateArt(art: ArtEntity)
    fun deleteArt(art: ArtEntity)
}

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val artRepository: ArtRepository
): ViewModel(), HomeViewModelAbstract{
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val artList: Flow<List<ArtEntity>> = artRepository.getAll()

    override fun addArt(art: ArtEntity) {
        ioScope.launch {
            artRepository.insert(art = art)
        }
    }

    override fun updateArt(art: ArtEntity) {
        ioScope.launch {
            artRepository.update(art = art)
        }
    }

    override fun deleteArt(art: ArtEntity) {
        ioScope.launch {
            artRepository.delete(art = art)
        }
    }
}