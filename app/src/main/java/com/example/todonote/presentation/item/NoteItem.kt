package com.example.todonote.presentation.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todonote.data.local.NoteEntity
import com.example.todonote.presentation.screens.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteItem(notes: NoteEntity){

    Card(
        modifier = Modifier.fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = notes.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notes.descr,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(4.dp))
           /* Text(
                text = SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.getDefault())
                    .format(Date(notes.time)),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )*/

            /*if (notes.image.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(note.image),
                    contentDescription = "Note Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }*/

        }
    }

}