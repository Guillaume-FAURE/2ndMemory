package com.example.composeproject.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.ui.home.HomeScreen
import com.example.composeproject.ui.theme.ComposeProjectTheme
import com.example.composeproject.viewmodel.HomeViewModelAbstract

enum class Screen {
    Home, Note
}

@Composable
fun NoteRoomApp(
    homeViewModel: HomeViewModelAbstract,
) {
    val navController = rememberNavController()
    ComposeProjectTheme() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.name,
                builder = {
                    composable(Screen.Home.name) {
                        HomeScreen(
                            homeViewModel = homeViewModel,
                        )
                    }
                    composable(Screen.Note.name) {

                    }
                })
        }
    }
}