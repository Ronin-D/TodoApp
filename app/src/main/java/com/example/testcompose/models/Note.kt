package com.example.testcompose.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Note(
   @PrimaryKey val id:String = UUID.randomUUID().toString(),
    val title:String,
    var deadlineTime:Date? = null,
    var deadlineDate: Date? = null
)