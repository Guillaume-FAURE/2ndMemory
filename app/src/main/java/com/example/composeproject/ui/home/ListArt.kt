package com.example.composeproject.ui.home

import android.content.Intent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.viewmodel.HomeViewModelAbstract

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListArt(
    arrayArt: List<ArtEntity>,
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
            items = arrayArt,
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
    val context = LocalContext.current
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT,
            "Title : ${art.title}\n" + "Author: ${art.author}\n" + "Description: ${art.description}\n"
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share anime_information")

    Column() {
        Row {
            Spacer(modifier = Modifier
                .width(20.dp)
                .fillMaxHeight())
            Text(
                text = art.type ?: "UnknownType",
                color = Color.White,
                fontSize = 25.sp
            )
        }
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
                        onLongPress = {
                            context.startActivity(shareIntent)
                        }
                    )
                }
                .height(150.dp)
                .background(MaterialTheme.colors.backgroundColor)
                .drawBehind {
                    val strokeWidth = 3.0.toFloat()
                    val y = size.height
                    drawLine(
                        Color.LightGray,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = art.picture,
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(
                    text = "Author : " + (art.author ?: "unknownAuthor"),
                    color = Color.White
                )
                Text(
                    text = "Title : " + (art.title ?: "unknownTitle"),
                    color = Color.White
                )
                Text(
                    text = art.description ?: "unknownDescription",
                    color = Color.White,
                    maxLines = 3,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(10.dp)
            ) {
                Text(
                    text = art.mark.toString() ?: "unknownMark",
                    color = Color.White
                )
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