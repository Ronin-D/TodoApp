package com.example.testcompose.ui.home_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcompose.models.Note
import com.example.testcompose.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeScreenViewModel:ViewModel() {

    private val databaseRepository = NoteRepository.get()

    var noteList = getNotes().asStateFlow()

    private fun getNotes():MutableStateFlow<SnapshotStateList<Note>>{
       val items = MutableStateFlow(mutableStateListOf<Note>())
        viewModelScope.launch(Dispatchers.IO) {
            val list  = databaseRepository.getNotes()
            items.value = mutableStateListOf(elements = list)
       }
        return items
    }

    fun removeNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.removeNote(note)
            delay(1000)//todo
            noteList.value.remove(note)
        }
    }
}