package com.example.todonote.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController //
import com.example.todonote.data.local.NoteDatabase
import com.example.todonote.domain.repository.NoteRepositoryImpl
import com.example.todonote.presentation.screens.NoteViewModel
import com.example.todonote.presentation.screens.Routes
import com.example.todonote.presentation.screens.add.AddScreen
import com.example.todonote.presentation.screens.home.HomeContact
import com.example.todonote.presentation.screens.home.HomeScreen
import com.example.todonote.presentation.screens.search.SearchScreen
import com.example.todonote.presentation.screens.trash.TrashScreen
import com.example.todonote.splash.SplashScreen

@Composable
fun MyApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val db = remember { NoteDatabase.getDataBase(context) }
    val repo = remember { NoteRepositoryImpl(db.noteDao()) }
    val viewModel = remember { NoteViewModel(repo) }

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) { SplashScreen(navController) }
        composable(Routes.HOME) { HomeScreen(viewModel , navController) }
        composable(Routes.HOMECONTACT) { HomeContact(viewModel) }
        composable(Routes.ADD) { AddScreen(viewModel , navController) }
        composable(Routes.SEARCH) { SearchScreen() }
        composable(Routes.TRASH) { TrashScreen() }
    }
}