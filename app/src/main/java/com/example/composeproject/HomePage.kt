package com.example.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.example.composeproject.ui.theme.ComposeProjectTheme
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.composeproject.ui.home.ArtCard
import com.example.composeproject.ui.home.HomeScreen

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}


data class Art(
    val author: String,
    val title: String,
    val imageURL: Painter,
    val description: String,
    val note: String,
    )

@Composable
fun Master(){
    Column {
        TopBar()

    }
}

@Composable
fun TopBar(){
    Row() {
        AsyncImage(model = "https://www.sfbok.se/sites/default/files/styles/1000x/sfbok/sfbokbilder/07/7481.jpg?bust=1481208506&itok=yVBceX0q" ,
            contentDescription = "logoApp")
        Text(text = "2nd Memory")
        Button(onClick = { /*TODO*/ }) {

        }
        Button(onClick = { /*TODO*/ }) {

        }
    }
}

