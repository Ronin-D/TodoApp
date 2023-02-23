package com.example.testcompose.ui

import android.text.format.DateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testcompose.models.Note
import com.example.testcompose.ui.home_screen.HomeScreenViewModel
import com.example.testcompose.util.Screens


private const val DATE_FORMAT = "EEEE, dd.MM.yyyy"
private const val TIME_FORMAT = "HH mm"

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val noteItems = viewModel.noteList.collectAsState()
 Box(
     modifier = Modifier
         .fillMaxSize()
         .background(Color(0xFF220052)),
 ){
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ){
        items(noteItems.value){ item: Note ->
            val isDeleted = rememberSaveable{
                mutableStateOf(false)
            }//todo
                ActivityItem(
                    note = item,
                    isDeleted = isDeleted.value,
                    onSelect = {
                        isDeleted.value = true//todo
                        viewModel.removeNote(item)//todo
                    }
                )
        }
    }
     AddItemBtn(
         modifier = Modifier
             .size(70.dp)
             .align(Alignment.BottomEnd)
             .padding(bottom = 16.dp, end = 16.dp),
         onClick = {
            navController.navigate(Screens.AddItemScreen.route)
         }
     )
 }
}



@Composable
fun AddItemBtn(onClick:()->Unit, modifier: Modifier){
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50)),
        modifier = modifier
    ) {
       Icon(
           imageVector = Icons.Default.Add,
           contentDescription = "Add icon"
       )
    }
}

@Composable
fun ActivityItem(note: Note, isDeleted:Boolean,onSelect:()->Unit){
    val interactionSource = remember {
        MutableInteractionSource()
    }
    AnimatedVisibility(
        visible = !isDeleted
    ) {
        Card(
            shape = RoundedCornerShape(14.dp),
            backgroundColor = Color(0xFFBB00FF),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(
                    indication = rememberRipple(bounded = true),
                    onClick = onSelect,
                    interactionSource = interactionSource
                ),
            elevation = 7.dp
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = note.title,
                    Modifier.padding(8.dp)
                )
                if (note.deadlineDate!=null){
                    Text(text = DateFormat.format(DATE_FORMAT,note.deadlineDate).toString(),
                        Modifier.padding(bottom = 8.dp)
                    )
                }
                if (note.deadlineTime!=null){
                    Text(text = DateFormat.format(TIME_FORMAT,note.deadlineTime).toString(),
                        Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }

}
