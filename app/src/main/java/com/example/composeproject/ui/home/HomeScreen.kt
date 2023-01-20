package com.example.composeproject.ui.home

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.viewmodel.HomeViewModelAbstract

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModelAbstract,
    onClickArt: (ArtEntity) -> Unit,
    onClickHome: () -> Unit,
    onClickToDo: () -> Unit,
    onClickDone: () -> Unit,

){
    val artListState = homeViewModel.artList.collectAsState(initial = listOf())
    val currentArtList = artListState.value.filter{
        art -> art.state == "Current"
    }
    val textState = rememberSaveable { mutableStateOf("") }
    val artIdState = rememberSaveable { mutableStateOf(0) }
    Scaffold(
        topBar = { FirstNavBar() },
        bottomBar = {
            SecondNavBar(
                1,
                onClickHome,
                onClickDone,
                onClickToDo,
            )
        }
    ) {
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
                ListArt(
                    currentArtList,
                    artIdState,
                    textState,
                    homeViewModel,
                    onClickArt,
                )
                Spacer(modifier = Modifier.padding(100.dp))
            }
        }
    }
}

