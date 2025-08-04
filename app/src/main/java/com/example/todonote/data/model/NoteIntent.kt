package com.example.todonote.data.model

import com.example.todonote.data.local.NoteEntity

sealed class NoteIntent {

    data class AddNote(val note: NoteEntity) : NoteIntent()
    object LoadNotes : NoteIntent()
    data class DeleteNote(val note: NoteEntity) : NoteIntent()
    data class UpdateNote(val note: NoteEntity) : NoteIntent()


}