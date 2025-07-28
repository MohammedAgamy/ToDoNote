package com.example.todonote.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.todonote.presentation.screens.Routes

data class Destination(
    val route: String,
    val icon: ImageVector,
    val label: String,
)

val bottomNavItems = listOf(
    Destination(Routes.HOMECONTACT, Icons.Default.Home, "Home"),
    Destination(Routes.ADD, Icons.Default.Add, "Add"),
    Destination(Routes.SEARCH, Icons.Default.Search, "Search"),
    Destination(Routes.TRASH, Icons.Default.Delete, "Trash")
)
