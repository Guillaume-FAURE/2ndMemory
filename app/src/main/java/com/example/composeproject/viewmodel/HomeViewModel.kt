package com.example.composeproject.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    val selectedArtState: State<ArtEntity?>
    val artList: Flow<List<ArtEntity>>
    fun addOrUpdateArt(art: ArtEntity)
    fun deleteArt(art: ArtEntity)
    fun selectArt(art: ArtEntity)
}

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val artRepository: ArtRepository
): ViewModel(), HomeViewModelAbstract{
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val _selectedArtState: MutableState<ArtEntity?> = mutableStateOf(null)
    override val selectedArtState: State<ArtEntity?>
        get() = _selectedArtState
    override val artList: Flow<List<ArtEntity>> = artRepository.getAll()

    override fun addOrUpdateArt(art: ArtEntity) {
        ioScope.launch {
            if (art.artId == null){
                artRepository.insert(art = art)
            }
            else {
                artRepository.update(art = art)
            }
        }
    }

    override fun deleteArt(art: ArtEntity) {
        ioScope.launch {
            artRepository.delete(art = art)
        }
    }

    override fun selectArt(art: ArtEntity) {
        _selectedArtState.value = art
    }
}