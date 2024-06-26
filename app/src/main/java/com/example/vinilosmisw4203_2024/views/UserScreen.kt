package com.example.vinilosmisw4203_2024.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import com.example.vinilosmisw4203_2024.R
import com.example.vinilosmisw4203_2024.viewsModels.AlbumViewModel
import com.example.vinilosmisw4203_2024.viewsModels.ArtistViewModel
import com.example.vinilosmisw4203_2024.viewsModels.CollectorViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        NavigationGraph(navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = Color.Gray,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.artist_icon),
                    contentDescription = "Artistas",
                    modifier = Modifier.size(40.dp)
                )
            },
            label = { Text("Artistas", fontSize = 10.sp) },
            selected = false,
            onClick = { navController.navigate("artists") },
            alwaysShowLabel = true
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.album_icon),
                    contentDescription = "Álbumes",
                    modifier = Modifier.size(40.dp)
                )
            },
            label = { Text("Álbumes", fontSize = 10.sp) },
            selected = false,
            onClick = { navController.navigate("albums") },
            alwaysShowLabel = true
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.collec_icon),
                    contentDescription = "Coleccionistas",
                    modifier = Modifier.size(40.dp)
                )
            },
            label = { Text("Coleccionistas", fontSize = 10.sp) },
            selected = false,
            onClick = { navController.navigate("collectors") },
            alwaysShowLabel = true
        )
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "artists") {
        composable("artists") {
            val viewModelArtist = viewModel<ArtistViewModel>()
            ArtistListScreen(viewModelArtist) }
        composable("collectors") {
            val viewModelCollector = viewModel<CollectorViewModel>()
            CollectorListScreen(viewModelCollector)
        }
        composable("albums") {
            val viewModelAlbum = viewModel<AlbumViewModel>()
            AlbumListScreen(viewModelAlbum)
        }
    }
}
