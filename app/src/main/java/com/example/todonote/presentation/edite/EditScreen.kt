package com.example.todonote.presentation.edite

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.example.todonote.data.model.NoteIntent
import com.example.todonote.presentation.screens.NoteViewModel
import com.example.todonote.presentation.screens.Routes
import com.example.todonote.presentation.screens.add.getCurrentDateTime
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditScreen(
    navController: NavHostController,
    viewModel: NoteViewModel,
    note: NoteEntity
) {
    var title by remember { mutableStateOf(note.title) }
    var descr by remember { mutableStateOf(note.descr) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = "Edit Note", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = descr,
                onValueChange = { descr = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    if (title.isNotBlank() && descr.isNotBlank()) {
                        val updatedNote = note.copy(
                            title = title,
                            descr = descr,
                            time = getCurrentDateTime() // تحديث الوقت مع التعديل
                        )
                        viewModel.onIntent(NoteIntent.UpdateNote(updatedNote))

                        scope.launch {
                            snackbarHostState.showSnackbar("Note updated")
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.HOME) { inclusive = true }
                            }
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please fill all fields")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Note")
            }
        }
    }
}