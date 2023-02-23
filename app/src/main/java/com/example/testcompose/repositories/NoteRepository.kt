package com.example.testcompose.repositories

import android.content.Context
import androidx.room.*
import com.example.testcompose.database.NoteDatabase
import com.example.testcompose.models.Note
import kotlinx.coroutines.flow.Flow

private const val DATABASE_NAME = "note_database"

class NoteRepository private
constructor(context: Context) {

    private val database:NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()

    private val noteDao = database.noteDao()

    suspend fun addNote(note: Note) = noteDao.addNote(note)

    suspend fun getNotes():Array<Note> = noteDao.getNotes()

    suspend fun removeNote(note: Note) = noteDao.removeNote(note)

    companion object {
        private var INSTANCE: NoteRepository? =
            null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    NoteRepository(context)
            }
        }
        fun get(): NoteRepository {
            return INSTANCE ?:
            throw
            IllegalStateException("NoteRepository must be initialized")
        }
    }
}