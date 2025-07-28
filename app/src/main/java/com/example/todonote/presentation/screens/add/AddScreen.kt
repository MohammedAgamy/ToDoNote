package com.example.todonote.presentation.screens.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todonote.data.local.NoteEntity
import com.example.todonote.data.model.NoteIntent
import com.example.todonote.presentation.screens.NoteViewModel

@Composable
fun AddScreen(
    viewModel: NoteViewModel

) {

    var title by remember { mutableStateOf("") }
    var descr by remember { mutableStateOf("") }


    Column(Modifier.padding(16.dp)) {


        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        OutlinedTextField(
            value = descr,
            onValueChange = { descr = it },
            label = { Text("Description") })

        Button(onClick = {
            val note = NoteEntity(
                title = title,
                descr = descr,
                image = "",
                time = ""
            )
            viewModel.onIntent(NoteIntent.AddNote(note))
        }) {
            Text("Add Note")
        }
    }
}