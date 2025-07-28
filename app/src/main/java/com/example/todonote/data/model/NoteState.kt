package com.example.todonote.data.model

import com.example.todonote.data.local.NoteEntity

data class NoteState(
    val notes: List<NoteEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)