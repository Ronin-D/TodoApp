package com.example.testcompose.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testcompose.ui.HomeScreen
import com.example.testcompose.ui.add_note_screen.AddNoteScreen
import com.example.testcompose.util.Screens


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ){
        composable(route = Screens.HomeScreen.route){
            HomeScreen(navController = navController)
        }

        composable(route = Screens.AddItemScreen.route){
            AddNoteScreen(navController = navController)
        }
    }
}