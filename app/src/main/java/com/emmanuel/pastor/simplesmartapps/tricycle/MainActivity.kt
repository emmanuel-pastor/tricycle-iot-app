package com.emmanuel.pastor.simplesmartapps.tricycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav.BottomNavigation
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav.NavigationGraph
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav.TopNavigation
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.TricycleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TricycleTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = hiltViewModel()
                val connectionState by viewModel.connectionState.collectAsState()

                Scaffold(
                    topBar = { TopNavigation(connectionState = connectionState, viewModel::onReconnectClicked) },
                    bottomBar = { BottomNavigation(navController = navController) },
                    content = { innerPadding ->
                        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(innerPadding)) {
                            NavigationGraph(navController = navController)
                        }
                    }
                )
            }
        }
    }
}