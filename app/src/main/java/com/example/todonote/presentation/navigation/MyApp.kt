package com.example.todonote.presentation.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController //
import com.example.todonote.data.local.NoteDatabase
import com.example.todonote.data.local.NoteEntity
import com.example.todonote.domain.repository.NoteRepositoryImpl
import com.example.todonote.presentation.screens.NoteViewModel
import com.example.todonote.presentation.screens.Routes
import com.example.todonote.presentation.screens.add.AddScreen
import com.example.todonote.presentation.edite.EditScreen
import com.example.todonote.presentation.screens.home.HomeContact
import com.example.todonote.presentation.screens.home.HomeScreen
import com.example.todonote.presentation.screens.search.SearchScreen
import com.example.todonote.presentation.screens.trash.TrashScreen
import com.example.todonote.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val db = remember { NoteDatabase.getDataBase(context) }
    val repo = remember { NoteRepositoryImpl(db.noteDao()) }
    val viewModel = remember { NoteViewModel(repo) }
    val entity = remember { NoteEntity() }

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) { SplashScreen(navController) }
        composable(Routes.HOME) { HomeScreen(viewModel , navController) }
        composable(Routes.HOMECONTACT) { HomeContact(viewModel, navController)  }
        composable(Routes.ADD) { AddScreen(viewModel ,navController) }
        composable(Routes.SEARCH) { SearchScreen(navController , viewModel) }
        composable(Routes.TRASH) { TrashScreen() }
        composable(Routes.Edit + "/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            noteId?.let { id ->
                val note = viewModel.getNoteById(id) // لازم تعمل فانكشن في الـ viewModel
                if (note != null) {
                    EditScreen(navController, viewModel, note)
                }
            }
        }
    }
}