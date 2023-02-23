package com.example.testcompose.database

import androidx.room.TypeConverter
import java.util.Date

class NoteTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?):Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate(date: Long?):Date?{
        return date?.let {
            Date(it)
        }
    }
}