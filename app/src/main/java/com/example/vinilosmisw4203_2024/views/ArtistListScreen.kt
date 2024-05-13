package com.example.vinilosmisw4203_2024.views
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.vinilosmisw4203_2024.models.Artist
import com.example.vinilosmisw4203_2024.viewsModels.ArtistViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ArtistListScreen(viewModel: ArtistViewModel) {
    val loading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf("") }
    val artists by viewModel.artists.observeAsState()

    LaunchedEffect(Unit) {
        try {
            viewModel.fetchArtists()
            loading.value = false
        } catch (e: Exception) {
            error.value = e.message ?: "Unknown error"
            loading.value = false
        }
    }

    if (loading.value) {
        CircularProgressIndicator()
    } else if (error.value.isNotEmpty()) {
        Text("Error: ${error.value}")
    } else {
        artists?.let { ArtistList(it) }
    }
}

@Composable
fun ArtistList(items: List<Artist>) {
    Column {
        Text(text = "12 VINILOS",
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold, fontSize = 30.sp)
        LazyColumn {
            items(items) { artist ->
                ArtistItem(artist)
            }
        }
    }
}

@Composable
fun ArtistItem(artist: Artist) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = artist.image,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = "Album Cover",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop // Ajusta cómo se ajusta la imagen dentro de su contenedor
            )
            Text(artist.name, style = MaterialTheme.typography.h6)
        }
    }
}