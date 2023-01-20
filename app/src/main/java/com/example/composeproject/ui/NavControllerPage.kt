package com.example.composeproject.ui

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination
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
    val context = LocalContext.current
    val navController = rememberNavController()

    val currentBackStack = navController.currentBackStackEntry
    val pageSave = currentBackStack?.savedStateHandle?.getLiveData<String>("key")
    if (currentBackStack != null) {
        Log.d("Navigation state", "getState : ${currentBackStack.savedStateHandle.get<String>("key")}")
    }
    if (pageSave != null) {
        Log.d("Navigation state", "PageSave : ${pageSave.value.toString()}")
    }
    else {
        Log.d("Navigation state", "PageSave : ERROR")
    }
    // Save the current navigation state when the user navigates to a new page
    SideEffect {
        if (currentBackStack != null) {
            saveNavigationState(currentBackStack.destination, context)
        }
        else {
            Log.d("Navigation state", "currentBackStack null")
        }
    }

    // handle the recovery process in case of a system kill
    LaunchedEffect(Unit) {
        val savedNavigationState = recoverNavigationState(navController, context)
        if (savedNavigationState != null) {
            navController.setGraph(savedNavigationState)
        }
    }

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
                        navController.previousBackStackEntry?.savedStateHandle?.set("key", "Home")
                        HomeScreen(
                            homeViewModel = homeViewModel,
                            onClickArt = { navController.navigate(Screen.Art.name) },
                            onClickHome= { navController.navigate(Screen.Home.name) },
                            onClickDone = { navController.navigate(Screen.Done.name) },
                            onClickToDo = { navController.navigate(Screen.ToDo.name) },
                        )
                    }
                    composable(Screen.Done.name) {
                        navController.previousBackStackEntry?.savedStateHandle?.set("key", "Done")
                        DoneScreen(
                            homeViewModel = homeViewModel,
                            onClickArt = { navController.navigate(Screen.Art.name) },
                            onClickHome= { navController.navigate(Screen.Home.name) },
                            onClickDone = { navController.navigate(Screen.Done.name) },
                            onClickToDo = { navController.navigate(Screen.ToDo.name) },
                        )
                    }
                    composable(Screen.ToDo.name) {
                        navController.previousBackStackEntry?.savedStateHandle?.set("key", "ToDo")
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

private fun saveNavigationState(destination: NavDestination, context: Context) {
    Log.d("Navigation state", destination.id.toString())
    destination.let {
        val sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("NAV_STATE", destination.id.toString()).apply()
    }
}

private fun recoverNavigationState(navController: NavController,context: Context): Int? {
    val sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
    val navState = sharedPreferences.getString("NAV_STATE", null)
    if (navState != null) {
        Log.d("Navigation state", navState)
    }
    else{
        Log.d("Navigation state", "ERROR")
    }
    return navState?.let {
        val destination = navController.graph.findNode(it)
        destination?.id ?: 0
    }
}
