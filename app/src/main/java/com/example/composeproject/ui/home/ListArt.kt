package com.example.composeproject.ui.home

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.viewmodel.HomeViewModelAbstract

@Composable
fun ListArt(
    arrayArt: State<List<ArtEntity>>,
    artIdState: MutableState<Int>,
    textState: MutableState<String>,
    homeViewModel: HomeViewModelAbstract,
    onClickArt: (ArtEntity) -> Unit,
){
    LazyColumn(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .height(600.dp)
    ) {
        items(arrayArt.value.size){
                index ->
            val art = arrayArt.value[index]
            ArtCard(
                art = art,
                artIdState,
                textState,
                homeViewModel,
                onClickArt
            )
        }
    }
}

@Composable
fun ArtCard(
    art: ArtEntity,
    artIdState: MutableState<Int>,
    textState: MutableState<String>,
    homeViewModel: HomeViewModelAbstract,
    onClickArt: (ArtEntity) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .pointerInput(Unit){
                detectTapGestures(
                    onTap = {
                        // popupState.value = PopupState.Edit
                        artIdState.value = art.artId!!
                        textState.value = art.title!!
                        homeViewModel.selectArt(art)
                        onClickArt(art)
                    },
                    onDoubleTap = {
                        homeViewModel.deleteArt(art)
                    }
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(model = "https://www.sfbok.se/sites/default/files/styles/1000x/sfbok/sfbokbilder/07/7481.jpg?bust=1481208506&itok=yVBceX0q",
            contentDescription = "image")
        Column {
            Text(
                text = art.author ?: "unknownAuthor",
                color = Color.White
            )
            Text(
                text = art.title ?: "unknownTitle",
                color = Color.White
            )
            Text(
                text = art.description ?: "unknownDescription",
                color = Color.White
            )
        }
        Column {
            Text(
                text = art.mark ?: "unknownMark",
                color = Color.White
            )
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
        }
    }
}