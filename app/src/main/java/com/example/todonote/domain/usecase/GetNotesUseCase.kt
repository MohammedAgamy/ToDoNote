package com.example.todonote.domain.usecase

import com.example.todonote.data.local.NoteEntity
import com.example.todonote.domain.repository.NoteRepository
import javax.inject.Inject


class GetNotesUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(): List<NoteEntity> {
        return repository.getAll()
    }
}
