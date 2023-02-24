package com.example.testcompose.ui.add_note_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcompose.models.Note
import com.example.testcompose.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class AddNoteViewModel: ViewModel() {

    private val dbRepository = NoteRepository.get()
    val titleText = mutableStateOf("")
    val dateButtonText = mutableStateOf("set up deadline date")
    val timeButtonText = mutableStateOf("set up deadline time")
    val deadlineDate = mutableStateOf<Date?>(null)
    val deadlineTime = mutableStateOf<Date?>(null)
    fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addNote(note)
        }
    }

    fun isNoteValid() = titleText.value.isNotBlank()

}