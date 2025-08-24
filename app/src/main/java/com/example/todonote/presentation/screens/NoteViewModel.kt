package com.example.todonote.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonote.data.local.NoteEntity
import com.example.todonote.data.model.NoteIntent
import com.example.todonote.data.model.NoteState
import com.example.todonote.domain.repository.NoteRepository
import com.example.todonote.presentation.item.NoteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repo: NoteRepository) : ViewModel() {


    private val _state = MutableStateFlow(NoteState())
    val state: StateFlow<NoteState> = _state



    init {
        viewModelScope.launch {
            repo.getAll()
            _state.value = _state.value.copy(repo.getAll())
        }
    }
    fun onIntent(intent: NoteIntent) {
        viewModelScope.launch {
            when (intent) {
                is NoteIntent.LoadNotes -> {
                    _state.value = _state.value.copy(isLoading = true)
                    try {
                        val note = repo.getAll()
                        _state.value = NoteState(note)
                    } catch (e: Exception) {
                        _state.value = NoteState(error = e.message)

                    }
                }

                is NoteIntent.AddNote -> {
                    repo.insert(intent.note)
                    onIntent(intent = NoteIntent.LoadNotes)
                }

                is NoteIntent.DeleteNote -> {
                    viewModelScope.launch {
                        repo.delete(intent.note)
                        onIntent(NoteIntent.LoadNotes)
                    }
                }

                is NoteIntent.UpdateNote -> viewModelScope.launch {
                    repo.updateNote(intent.note)
                    onIntent(NoteIntent.LoadNotes) // reload after update
                }
            }

        }
    }
    fun getNoteById(id: Int): NoteEntity? {
        return state.value.notes.find { it.id == id }
    }

}