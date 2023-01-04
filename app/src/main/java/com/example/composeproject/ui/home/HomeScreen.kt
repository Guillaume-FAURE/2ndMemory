package com.example.composeproject.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.viewmodel.HomeViewModelAbstract

enum class PopupState {
    Open, Close, Edit
}

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModelAbstract
){
    val artListState = homeViewModel.artList.collectAsState(initial = listOf())
    val textState = rememberSaveable() { mutableStateOf("") }
    val artIdState = rememberSaveable { mutableStateOf(0) }
    val popupState = rememberSaveable { mutableStateOf(PopupState.Close) }

    Scaffold() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
                .width(1.dp)
                .border(1.dp, Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                FirstNavBar()
                SecondNavBar(1)
                ListArt(artListState, popupState, artIdState, textState, homeViewModel)
                Spacer(modifier = Modifier.padding(100.dp))
            }
        }
        Button(onClick = {
            popupState.value = PopupState.Open
        },
        modifier = Modifier.offset(x = 300.dp, y=600.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red
        )) {
            Text(text = "Add Art")
        }
    }

    when(popupState.value){
        PopupState.Open -> {
            ArtPopup(
                onClickDismiss = {
                    popupState.value = PopupState.Close
                },
                onClickSave = {
                    homeViewModel.addArt(art = ArtEntity(
                        author = "Unknown",
                        title = it,
                        description = "Unknown",
                        mark = "?/10",
                        state = "ToDo",
                        type = "Manga",
                    )
                    )
                    popupState.value = PopupState.Close
                },
            )
        }
        PopupState.Edit -> {
            ArtPopup(
                title = textState.value,
                onClickDismiss = {
                    popupState.value = PopupState.Close
                },
                onClickSave = {
                    homeViewModel.updateArt(
                        art = ArtEntity(
                            artId = artIdState.value,
                            author = "Unknown",
                            title = it,
                            description = "Unknown",
                            mark = "?/10",
                            state = "ToDo",
                            type = "Manga",
                        )
                    )
                    popupState.value = PopupState.Close
                },
            )
        }
        PopupState.Close -> {
        }
    }
}

@Composable
fun ListArt(
    arrayArt: State<List<ArtEntity>>,
    popupState: MutableState<PopupState>,
    artIdState: MutableState<Int>,
    textState: MutableState<String>,
    homeViewModel: HomeViewModelAbstract,
){
    LazyColumn(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .height(600.dp)
    ) {
        items(arrayArt.value.size){
                index ->
            val art = arrayArt.value[index]
            ArtCard(art = art, popupState, artIdState, textState, homeViewModel)
        }
    }
}

@Composable
fun ArtCard(
    art: ArtEntity,
    popupState: MutableState<PopupState>,
    artIdState: MutableState<Int>,
    textState: MutableState<String>,
    homeViewModel: HomeViewModelAbstract
){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .pointerInput(Unit){
               detectTapGestures(
                   onTap = {
                       popupState.value = PopupState.Edit
                       artIdState.value = art.artId!!
                       textState.value = art.title!!
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