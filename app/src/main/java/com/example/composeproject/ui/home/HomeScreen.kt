package com.example.composeproject.ui.home

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.HomePage
import com.example.composeproject.ParameterPage
import com.example.composeproject.model.ArtEntity

val ArrayArt: List<ArtEntity> = listOf(
    ArtEntity(
    author = "Monsieur 1",
    title = "Book 1",
    description = "Description 1",
    mark = "8/20",
    type = "book",
    state = "todo")
)


@Composable
fun HomeScreen(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        FirstNavBar()
        SecondNavBar(1)
        ListArt(ArrayArt)
        Spacer(modifier = Modifier.padding(100.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add Art")
        }
    }

}

@Composable
fun FirstNavBar(){
    val context = LocalContext.current
    Row {
        AsyncImage(model = "https://www.sfbok.se/sites/default/files/styles/1000x/sfbok/sfbokbilder/07/7481.jpg?bust=1481208506&itok=yVBceX0q" ,
            contentDescription = "logoApp")
        Text(text = "2nd Memory")
        Button(onClick = { context.startActivity(Intent(context, ParameterPage::class.java)) }) {
            AsyncImage(model = "https://www.sfbok.se/sites/default/files/styles/1000x/sfbok/sfbokbilder/07/7481.jpg?bust=1481208506&itok=yVBceX0q" ,
                contentDescription = "logoApp")
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
fun ListArt(arrayArt: List<ArtEntity>){
    LazyColumn {
        items(arrayArt){
            art -> ArtCard(art = art)
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