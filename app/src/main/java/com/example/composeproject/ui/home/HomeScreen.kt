package com.example.composeproject.ui.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.viewmodel.HomeViewModelAbstract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModelAbstract
){
    val artListState = homeViewModel.artList.collectAsState(initial = listOf())
    Scaffold() {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(MaterialTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.6f)
                .width(1.dp)
                .border(1.dp, Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                FirstNavBar()
                SecondNavBar(1)
                ListArt(artListState)
                Spacer(modifier = Modifier.padding(100.dp))

            }
            Button(onClick = {
                homeViewModel.addArt(
                    ArtEntity(author = "Author1",
                        title = "Title1",
                        description = "Description1",
                        mark = "mark1",
                        type = "book",
                        state = "todo"
                    )
                )
            }) {
                Text(text = "Add Art")
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen(homeViewModel = object : HomeViewModelAbstract {
        override val artList: Flow<List<ArtEntity>>
            get() = flowOf(listOf(
                ArtEntity(author = "Author1",
                    title = "Title1",
                    description = "Description1",
                    mark = "mark1",
                    type = "book",
                    state = "todo"
                ),
                ArtEntity(author = "Author2",
                    title = "Title2",
                    description = "Description2",
                    mark = "mark2",
                    type = "book",
                    state = "todo"
                ))
            )
        override fun addArt(art: ArtEntity) {}
        override fun updateArt(art: ArtEntity) {}
        override fun deleteArt(art: ArtEntity) {}

    })
}

@Composable
fun FirstNavBar(){
    val context = LocalContext.current
    Row {
        AsyncImage(model = "https://www.sfbok.se/sites/default/files/styles/1000x/sfbok/sfbokbilder/07/7481.jpg?bust=1481208506&itok=yVBceX0q" ,
            contentDescription = "logoApp", modifier = Modifier.size(100.dp))
        Text(text = "2nd Memory")
        Button(onClick = { /* context.startActivity(Intent(context, ParameterPage::class.java)) */ }) {
            AsyncImage(model = "https://www.sfbok.se/sites/default/files/styles/1000x/sfbok/sfbokbilder/07/7481.jpg?bust=1481208506&itok=yVBceX0q" ,
                contentDescription = "logoApp", modifier = Modifier.size(100.dp))
        }
    }
}

@Composable
fun SecondNavBar(currentPage: Int){
    Row(modifier = Modifier.fillMaxWidth(1f)) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Home",
                style = if(currentPage==1){ TextStyle(textDecoration = TextDecoration.Underline)
                } else {
                    TextStyle(textDecoration = TextDecoration.None)
                }
            )

        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Done",
                style = if(currentPage==2){ TextStyle(textDecoration = TextDecoration.Underline)
                } else {
                    TextStyle(textDecoration = TextDecoration.None)
                })
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "To Do",
                style = if(currentPage==3){ TextStyle(textDecoration = TextDecoration.Underline)
                } else {
                    TextStyle(textDecoration = TextDecoration.None)
                })
        }
    }
}

@Composable
fun ListArt(arrayArt: State<List<ArtEntity>>){
    LazyColumn {
        items(arrayArt.value.size){
                index ->
            val art = arrayArt.value[index]
            ArtCard(art = art)
        }
    }
}

@Composable
fun ArtCard(art: ArtEntity){
    Row(modifier = Modifier.fillMaxWidth(1f)) {
        AsyncImage(model = "https://www.sfbok.se/sites/default/files/styles/1000x/sfbok/sfbokbilder/07/7481.jpg?bust=1481208506&itok=yVBceX0q",
            contentDescription = "image")
        Column {
            Text(text = art.author ?: "unknownAuthor")
            Text(text = art.title ?: "unknownTitle")
            Text(text = art.description ?: "unknownDescription")
        }
        Column {
            Text(text = art.mark ?: "unknownMark")
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
        }
    }
}