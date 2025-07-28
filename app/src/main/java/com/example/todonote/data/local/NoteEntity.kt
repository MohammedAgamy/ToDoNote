package com.example.todonote.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteFile")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val descr: String = "",
    val image: String = "",
    val time : String = "",
)
