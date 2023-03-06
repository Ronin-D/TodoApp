package com.example.testcompose.repositories

import android.content.Context
import androidx.room.*
import com.example.testcompose.database.NoteDatabase
import com.example.testcompose.models.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NoteRepository @Inject constructor(
    database: NoteDatabase
) {

    private val noteDao = database.noteDao()

    suspend fun addNote(note: Note) = noteDao.addNote(note)

    suspend fun getNotes():Array<Note> = noteDao.getNotes()

    suspend fun removeNote(note: Note) = noteDao.removeNote(note)

}