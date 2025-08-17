package com.example.todonote.presentation.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todonote.data.model.NoteIntent
import com.example.todonote.presentation.item.NoteItem
import com.example.todonote.presentation.screens.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContact(noteViewModel: NoteViewModel) {
    val state by noteViewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        noteViewModel.onIntent(NoteIntent.LoadNotes)
    }
    Spacer(modifier = Modifier.height(16.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
        if (state.notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No notes yet", color = Color.Gray)
            }
        } else {
            // Display the list of notes 
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.notes) { note ->
                    var visible by remember { mutableStateOf(true) }

                    AnimatedVisibility(
                        visible = visible,
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        NoteItem(
                            notes = note,
                            onDelete = {
                                visible = false

                                LaunchedEffect(note.id) {
                                    kotlinx.coroutines.delay(300) // نفس مدة الـ exit
                                    noteViewModel.onIntent(NoteIntent.DeleteNote(note))
                                }
                                noteViewModel.onIntent(NoteIntent.DeleteNote(note))
                                scope.launch {
                                    snackbarHostState.showSnackbar(" Note deleted successfully")

                                }

                            },
                            onEdit = {
                                //
                            },
                            modifier = Modifier.animateItemPlacement()

                        )
                    }



                }

            }
        }


    }
}