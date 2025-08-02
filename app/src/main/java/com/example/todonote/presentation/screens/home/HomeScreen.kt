package com.example.todonote.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todonote.presentation.screens.NoteViewModel
import com.example.todonote.presentation.screens.Routes
import com.example.todonote.presentation.screens.add.AddScreen
import com.example.todonote.presentation.screens.search.SearchScreen
import com.example.todonote.presentation.screens.trash.TrashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: NoteViewModel , navHostController: NavHostController) {
    var selectedTab by remember { mutableStateOf(Routes.HOMECONTACT) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = selectedTab == item.route,
                        onClick = {
                            selectedTab = item.route
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                Routes.HOMECONTACT -> HomeContact(viewModel)
                Routes.ADD -> AddScreen(viewModel , navController = navHostController ) // Replace with actual NavHostController
                Routes.SEARCH -> SearchScreen()
                Routes.TRASH -> TrashScreen()
            }
        }
    }
}