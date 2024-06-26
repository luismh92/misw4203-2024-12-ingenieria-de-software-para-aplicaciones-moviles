package com.example.vinilosmisw4203_2024.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.vinilosmisw4203_2024.models.Album
import com.example.vinilosmisw4203_2024.viewsModels.AlbumViewModel

@Composable
fun AlbumListScreen(viewModel: AlbumViewModel) {
    var showDetailDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }

    val loading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf("") }
    val albums by viewModel.albums.observeAsState()

    LaunchedEffect(Unit) {
        try {
            viewModel.fetchAlbums()
            loading.value = false
        } catch (e: Exception) {
            error.value = e.message ?: "Error desconocido"
            loading.value = false
        }
    }

    if (loading.value) {
        CircularProgressIndicator()
    } else if (error.value.isNotEmpty()) {
        Text("Error: ${error.value}")
    } else {
        albums?.let {
            AlbumList(it) { album ->
                selectedAlbum = album
                showDetailDialog = true
            }
        }
    }

    selectedAlbum?.let {
        if (showDetailDialog) {
            AlbumDetailDialog(album = it, onDismissRequest = { showDetailDialog = false })
        }
    }
}

@Composable
fun AlbumList(albums: List<Album>, onAlbumClick: (Album) -> Unit) {

    val isDark = isSystemInDarkTheme()
    Column {
        Text(
            text = "12 VINILOS",
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
            fontSize = 30.sp,
            color = if (isDark) Color.White else Color.Black
        )
        LazyColumn {
            items(albums) { album ->
                AlbumItem(album, onAlbumClick)
            }
        }}
}

@Composable
fun AlbumItem(album: Album, onAlbumClick: (Album) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onAlbumClick(album) },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = album.cover),
                contentDescription = "Portada del Álbum",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(album.name, fontSize = 18.sp)
            Text(album.genre, fontSize = 14.sp)
        }
    }
}

@Composable
fun AlbumDetailDialog(album: Album, onDismissRequest: () -> Unit) {
    val isDark = isSystemInDarkTheme()
    val color = if (isDark) Color.White else Color.Black

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(text = album.name, color=color)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberImagePainter(album.cover),
                    contentDescription = "Portada del Álbum",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text("Género: ${album.genre}", color=color)
                Text("Fecha de lanzamiento: ${album.releaseDate}", color=color)
                Text("Descripción: ${album.description}", color=color)
                Text("Sello discográfico: ${album.recordLabel}", color=color)
            }
        },
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("Cerrar", color=color)
            }
        }
    )
}
