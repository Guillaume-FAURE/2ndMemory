package com.example.composeproject.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.ui.art.ArtScreen
import com.example.composeproject.ui.home.DoneScreen
import com.example.composeproject.ui.home.HomeScreen
import com.example.composeproject.ui.home.ToDoScreen
import com.example.composeproject.ui.theme.ComposeProjectTheme
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.viewmodel.HomeViewModelAbstract
import dagger.hilt.android.HiltAndroidApp

enum class Screen {
    Home, Done, ToDo, Art
}

@Composable
fun NavControllerPage(
    homeViewModel: HomeViewModelAbstract,
) {
    val navController = rememberNavController()
    ComposeProjectTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.backgroundColor
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.name,
                builder = {
                    composable(Screen.Home.name) {
                        HomeScreen(
                            homeViewModel = homeViewModel,
                            onClickArt = { navController.navigate(Screen.Art.name) },
                            onClickHome= { navController.navigate(Screen.Home.name) },
                            onClickDone = { navController.navigate(Screen.Done.name) },
                            onClickToDo = { navController.navigate(Screen.ToDo.name) },
                        )
                    }
                    composable(Screen.Done.name) {
                        DoneScreen(
                            homeViewModel = homeViewModel,
                            onClickArt = { navController.navigate(Screen.Art.name) },
                            onClickHome= { navController.navigate(Screen.Home.name) },
                            onClickDone = { navController.navigate(Screen.Done.name) },
                            onClickToDo = { navController.navigate(Screen.ToDo.name) },
                        )
                    }
                    composable(Screen.ToDo.name) {
                        ToDoScreen(
                            homeViewModel = homeViewModel,
                            onClickArt = { navController.navigate(Screen.Art.name) },
                            onClickHome= { navController.navigate(Screen.Home.name) },
                            onClickDone = { navController.navigate(Screen.Done.name) },
                            onClickToDo = { navController.navigate(Screen.ToDo.name) },
                        )
                    }
                    composable(Screen.Art.name) {
                        val art = homeViewModel.selectedArtState.value
                        if (art != null) {
                            ArtScreen(
                                art,
                                onClickClose = { navController.popBackStack() },
                                homeViewModel = homeViewModel
                            )
                        }
                    }
                })
        }
    }
}
