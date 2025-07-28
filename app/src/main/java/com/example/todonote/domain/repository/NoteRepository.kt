package com.example.todonote.domain.repository

import com.example.todonote.data.local.NoteEntity

interface NoteRepository {

    suspend fun getAll(): List<NoteEntity>
    suspend fun insert(note: NoteEntity)
    suspend fun delete(note: NoteEntity)
}