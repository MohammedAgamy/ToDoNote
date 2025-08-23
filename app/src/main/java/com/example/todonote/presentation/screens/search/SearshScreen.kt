package com.example.todonote.presentation.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todonote.data.local.NoteEntity
import com.example.todonote.presentation.item.NoteItem
import com.example.todonote.presentation.screens.NoteViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: NoteViewModel
) {

    var query by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val notes = viewModel.state.value.notes
    val filteredNotes = remember(query, notes) {
        if (query.isBlank()) notes
        else notes.filter {
            it.title.contains(query, ignoreCase = true) || it.descr.contains(
                query,
                ignoreCase = true
            )
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search Notes") },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredNotes) { note: NoteEntity ->
                    NoteItem(
                        notes = note,
                        onDelete = {
                            viewModel.onIntent(
                                com.example.todonote.data.model.NoteIntent.DeleteNote(
                                    it
                                )
                            )
                            scope.launch {
                                snackbarHostState.showSnackbar("Note deleted")
                            }
                        },
                        onEdit = {
                            navController.navigate("edit/${note.id}")
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }


}