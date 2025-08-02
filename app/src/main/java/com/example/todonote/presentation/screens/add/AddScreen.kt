package com.example.todonote.presentation.screens.add


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.todonote.R
import com.example.todonote.data.local.NoteEntity
import com.example.todonote.data.model.NoteIntent
import com.example.todonote.presentation.screens.NoteViewModel
import com.example.todonote.presentation.screens.Routes
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    viewModel: NoteViewModel, navController: NavHostController
) {
    var title by remember { mutableStateOf("") }
    var descr by remember { mutableStateOf("") }
    val dateTime = remember { getCurrentDateTime() }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    // tack image
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            imageUri = saveBitmapToCache(context, it)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    var openCamera by remember { mutableStateOf(false) }
    var openGallery by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val imageGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            permissions[Manifest.permission.READ_MEDIA_IMAGES] ?: false
        else
            permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

        val activity = context as Activity

        if (!cameraGranted && !imageGranted) {
            val shouldShowCameraRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.CAMERA
            )
            val shouldShowGalleryRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    Manifest.permission.READ_MEDIA_IMAGES
                else
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )

            if (!shouldShowCameraRationale && !shouldShowGalleryRationale) {
                // رفض دائم
                scope.launch {
                    snackbarHostState.showSnackbar("اذهب إلى الإعدادات لتفعيل الصلاحيات")
                }
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                    context.startActivity(this)
                }
            } else {
                // رفض مؤقت
                scope.launch {
                    snackbarHostState.showSnackbar("الرجاء السماح بالصلاحيات")
                }
            }
        } else {
            // كل الصلاحيات تمام
            if (openCamera) {
                cameraLauncher.launch(null)
                openCamera = false
            } else if (openGallery) {
                galleryLauncher.launch("image/*")
                openGallery = false
            }
        }
    }
    Scaffold(
        snackbarHost = { androidx.compose.material3.SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {

                val note = NoteEntity(
                    title = title, descr = descr, image = imageUri.toString(), time = dateTime
                )
                if (note.title.isEmpty() || note.descr.isEmpty()) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Please fill in all fields")
                    }
                } else {
                    viewModel.onIntent(NoteIntent.AddNote(note))
                    scope.launch {
                        snackbarHostState.showSnackbar("Note added successfully!")
                    }
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Title") },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),

                    )
                Image(
                    modifier = Modifier
                        .size(38.dp)
                        .clickable(onClick = {
                            openGallery = true
                            requestPermissions(permissionLauncher)
                        }),
                    painter = painterResource(R.drawable.picture),
                    contentDescription = "Add Image",

                    )
            }


            TextField(
                value = descr,
                onValueChange = { descr = it },
                placeholder = { Text("Description") },
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(top = 12.dp)

            )
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM d HH:mm")
    return current.format(formatter)
}


fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.png")
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        it.flush()
    }
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}

fun requestPermissions(permissionLauncher: androidx.activity.result.ActivityResultLauncher<Array<String>>) {
    permissionLauncher.launch(
        arrayOf(
            Manifest.permission.CAMERA,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                Manifest.permission.READ_MEDIA_IMAGES
            else
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )
}