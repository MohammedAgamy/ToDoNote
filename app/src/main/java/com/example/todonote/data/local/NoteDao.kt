package com.example.todonote.data.local

import androidx.room.*

@Dao
interface NoteDao {
// Insert a new note into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    // Delete a note from the database
    @Delete
    suspend fun deleteNote(note: NoteEntity)

    // Retrieve all notes from the database
    @Query("SELECT * FROM NoteFile")
    suspend fun getAllNotes(): List<NoteEntity>

}
