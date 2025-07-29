package com.example.todonote.presentation.screens.home
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todonote.presentation.item.NoteItem
import com.example.todonote.presentation.screens.NoteViewModel

@Composable
fun HomeContact (noteViewModel: NoteViewModel){
    val state by noteViewModel.state.collectAsState()

    Column (modifier = Modifier.fillMaxWidth()){

        if (state.notes.isEmpty())
        {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No notes yet", color = Color.Gray)
            }
        } else {
            // Display the list of notes
            state.notes.forEach { note ->
                NoteItem(notes = note)
            }

        }
    }


}