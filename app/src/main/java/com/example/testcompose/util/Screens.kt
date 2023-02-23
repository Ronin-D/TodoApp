package com.example.testcompose.util

sealed class Screens(
    val route:String
){
    object HomeScreen:Screens(route = "home_screen")
    object AddItemScreen:Screens(route = "add_item_screen")
}
