package com.example.todonote.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonote.data.model.NoteIntent
import com.example.todonote.data.model.NoteState
import com.example.todonote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val repo: NoteRepository): ViewModel() {


    private val _state = MutableStateFlow(NoteState())
    val state: StateFlow<NoteState> = _state


    fun onIntent(intent: NoteIntent) {
        viewModelScope.launch {
            when (intent) {
                is NoteIntent.LoadNotes -> {
                    _state.value = _state.value.copy(isLoading = true)
                    try {
                        val note = repo.getAll()
                        _state.value = NoteState(note)
                    }
                    catch (e: Exception){
                        _state.value = NoteState(error = e.message)

                    }
                }

                is NoteIntent.AddNote -> {
                   repo.insert(intent.note)
                    onIntent(intent = NoteIntent.LoadNotes)
                }

                is NoteIntent.DeleteNote -> TODO()
            }
        }

    }




}