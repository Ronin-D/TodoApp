package com.example.testcompose.database

import androidx.room.*
import com.example.testcompose.models.Note

@Dao
interface NoteDao {

    @Insert
    fun addNote(note: Note)

    @Query("SELECT * FROM note")
    fun getNotes():Array<Note>

    @Delete
    fun removeNote(note: Note)

}