package com.example.composeproject

sealed class Screen(val route: String){
    object HomeScreen : Screen(route = "home_screen")
    object ParemeterScreen : Screen(route = "parameter_screen")
}
