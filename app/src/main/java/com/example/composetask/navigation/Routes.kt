package com.example.composetask.navigation



sealed class Route(val route: String) {
    object Home : Route("home")
    object Chat : Route("chat")
    object Profile : Route("profile")
}