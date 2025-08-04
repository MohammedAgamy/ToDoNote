package com.example.todonote.domain.repository

import com.example.todonote.data.local.NoteDao
import com.example.todonote.data.local.NoteEntity

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {

   // Fetch all notes from the database
    override suspend fun getAll(): List<NoteEntity> {
        return dao.getAllNotes()
    }

    // Insert a new note into the database
    override suspend fun insert(note: NoteEntity) {
        dao.insertNote(note)
    }

    // Delete a note from the database
    override suspend fun delete(note: NoteEntity) {
        dao.deleteNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        dao.updateNote(note)
    }
}