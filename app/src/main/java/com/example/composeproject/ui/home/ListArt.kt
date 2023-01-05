package com.example.composeproject.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.viewmodel.HomeViewModelAbstract

@OptIn(ExperimentalMaterialApi::class)
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
        items(
            items = arrayArt.value,
            key = {it.artId ?: ""}
        ){
                art ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart ||
                        it == DismissValue.DismissedToEnd) {
                        // delete the item from database
                        homeViewModel.deleteArt(art)

                        return@rememberDismissState true
                    }
                    return@rememberDismissState false
                }
            )
            SwipeToDismissArtCard(
                art = art,
                artIdState,
                textState,
                homeViewModel,
                onClickArt,
                dismissState
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
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // popupState.value = PopupState.Edit
                        artIdState.value = art.artId!!
                        textState.value = art.title!!
                        homeViewModel.selectArt(art)
                        onClickArt(art)
                    },
                )
            }
            .background(MaterialTheme.colors.backgroundColor),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissArtCard (
    art: ArtEntity,
    artIdState: MutableState<Int>,
    textState: MutableState<String>,
    homeViewModel: HomeViewModelAbstract,
    onClickArt: (ArtEntity) -> Unit,
    dismissState: DismissState
){
    SwipeToDismiss(
        state = dismissState,
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.Red
                    DismissValue.DismissedToEnd -> Color.Red
                    DismissValue.DismissedToStart -> Color.Red
                }
            )
            val alignment = Alignment.CenterEnd
            val icon = Icons.Default.Delete
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = color)
                    .padding(8.dp)
            ){
                Column(
                    modifier = if (direction==DismissDirection.StartToEnd) {
                        Modifier.align(Alignment.CenterStart)
                    }
                    else {
                        Modifier.align(Alignment.CenterEnd)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                    Text(
                        text = "Delete",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        },
        dismissContent = {
            ArtCard(
                art = art,
                artIdState = artIdState,
                textState = textState,
                homeViewModel = homeViewModel,
                onClickArt = onClickArt
            )
        }
    )
}