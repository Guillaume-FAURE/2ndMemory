package com.example.composeproject.model

import androidx.compose.runtime.MutableState

data class ArtInformationState (
    val titleState: MutableState<String?>,
    val typeState : MutableState<String?>,
    val stateState : MutableState<String?>,
    val authorState : MutableState<String?>,
    val descriptionState : MutableState<String?>,
    val markState : MutableState<String?>,
    val pictureState: MutableState<String?>,
)