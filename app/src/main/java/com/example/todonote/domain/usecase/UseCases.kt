package com.example.todonote.domain.usecase


import javax.inject.Inject


data class UseCases @Inject constructor(
    val addNote: AddNoteUseCase,
    val getNotes: GetNotesUseCase
)
