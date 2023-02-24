package com.example.testcompose.ui.add_note_screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.text.format.DateFormat
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testcompose.models.Note
import com.example.testcompose.util.Screens
import java.util.Calendar
import java.util.Date


private const val DATE_FORMAT = "EEEE, dd.MM.yyyy"
private const val TIME_FORMAT = "HH:mm"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNoteScreen(
    navController: NavController,
    viewModel: AddNoteViewModel = viewModel()
) {

    val focusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current

    val isInputValid = rememberSaveable {
        mutableStateOf(true)
    }
    val context  = LocalContext.current
    val date =Calendar.getInstance()

    Box(modifier = Modifier
        .fillMaxSize()){
        IconButton(
            onClick = {
            navController.navigate(Screens.HomeScreen.route){
                popUpTo(Screens.HomeScreen.route){
                    inclusive = true
                }
            }
        },
        modifier = Modifier
            .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "arrow back"
            )
        }

        Column(modifier = Modifier.align(Alignment.Center)) {
            TextInput(
                textValue =viewModel.titleText.value,
                placeHolder ="type your note",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            isInputValid.value = true
                        }
                    },
                label = "Note",
                isValidState = isInputValid.value,
                onValueChange ={
                    viewModel.titleText.value = it
                }
            )
            Button(
                onClick =
                {
                    focusManager.clearFocus()
                    datePicker(
                       context = context,
                       year = date[Calendar.YEAR],
                       month = date[Calendar.MONTH],
                       day = date[Calendar.DAY_OF_MONTH],
                       onSelect = {
                            viewModel.deadlineDate.value = it
                           viewModel.dateButtonText.value = DateFormat.format(
                               DATE_FORMAT,
                               it
                           ).toString()
                       }
                   )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = viewModel.dateButtonText.value)
            }
            Button(
                onClick =
                {
                    focusManager.clearFocus()
                    timePicker(
                        context = context,
                        hours = date[Calendar.HOUR],
                        minutes = date[Calendar.MINUTE],
                        onSelect = {
                            viewModel.deadlineTime.value = it
                            viewModel.timeButtonText.value = DateFormat.format(
                                TIME_FORMAT,
                                it
                            ).toString()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text =viewModel.timeButtonText.value)
            }
      }
        Button(
            onClick =
            {
                focusManager.clearFocus()
                if (viewModel.isNoteValid()){
                    viewModel.addNote(
                        Note(
                            title = viewModel.titleText.value,
                            deadlineDate = viewModel.deadlineDate.value,
                            deadlineTime = viewModel.deadlineTime.value
                        )
                    )
                    navController.navigate(Screens.HomeScreen.route){
                        popUpTo(Screens.HomeScreen.route){
                            inclusive = true
                        }
                    }
                }
                else{
                    isInputValid.value = false
                }

            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Confirm")
        }
    }
}

@Composable
fun TextInput (
    textValue:String,
    placeHolder:String,
    modifier: Modifier,
    label:String,
    isValidState:Boolean,
    onValueChange:(String)->Unit
){

    val unfocusedBorderColor:Color = if (isValidState){
        Color.Green
    }
    else{
        Color.Red
    }
    Column(modifier = modifier) {
        OutlinedTextField(
            value =textValue,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(text = placeHolder, color = Color.LightGray)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = unfocusedBorderColor,
                focusedBorderColor = Color.Green,
                disabledBorderColor = Color.Green,
                focusedLabelColor = Color.Green,
                unfocusedLabelColor = unfocusedBorderColor
            ),
            label = {
                Text(text = label)
            }
        )
        AnimatedVisibility(visible = !isValidState) {
            Text(text = "Это обязательное поле", color = unfocusedBorderColor)
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
fun datePicker(
    context: Context,
    year:Int,
    month:Int,
    day:Int,
    onSelect:(date:Date)->Unit
) {
    DatePickerDialog(
        context,
        {  _:DatePicker, selectedYear:Int, selectedMonth:Int, selectedDay:Int->

            val date = Calendar.getInstance()
            date.set(selectedYear,selectedMonth,selectedDay)
            onSelect(Date.from(date.toInstant()))

        },
        year,month,day
    ).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun timePicker(
    context: Context,
    hours:Int,
    minutes:Int,
    onSelect:(date:Date)->Unit
) {
    TimePickerDialog(
        context,
        {  _, selectedHours:Int,selectedMinutes:Int->

            val date = Calendar.getInstance()
            date.time.hours = selectedHours
            date.time.minutes = selectedMinutes
            onSelect(Date.from(date.toInstant()))
        },
        hours,
        minutes,
        true
    ).show()
}


