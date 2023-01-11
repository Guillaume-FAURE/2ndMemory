package com.example.composeproject.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.viewmodel.HomeViewModelAbstract

@Composable
fun ToDoScreen(
    homeViewModel: HomeViewModelAbstract,
    onClickArt: (ArtEntity) -> Unit,
    onClickHome: () -> Unit,
    onClickToDo: () -> Unit,
    onClickDone: () -> Unit,

){
    val artListState = homeViewModel.artList.collectAsState(initial = listOf())
    val toDoArtList = artListState.value.filter{
            art -> art.state == "ToDo"
    }
    val textState = rememberSaveable { mutableStateOf("") }
    val artIdState = rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = { FirstNavBar() },
        bottomBar = {
            SecondNavBar(
                3,
                onClickHome,
                onClickDone,
                onClickToDo,
            )
        }
    ) {
        Column(
            modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .background(MaterialTheme.colors.backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
                .width(1.dp)
                .border(1.dp, Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ListArt(
                    toDoArtList,
                    artIdState,
                    textState,
                    homeViewModel,
                    onClickArt
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.87f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Button(onClick = {
            val art = ArtEntity(
                author = "",
                title = "",
                description = "",
                mark = "",
                state = "",
                type = "",
                picture = "",
            )
            homeViewModel.selectArt(art)
            onClickArt(art)
        },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red
            )) {
            Text(text = "Add Art")
        }
    }
}