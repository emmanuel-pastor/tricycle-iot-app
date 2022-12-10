package com.emmanuel.pastor.simplesmartapps.tricycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav.BottomNavigation
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav.NavigationGraph
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.TricycleTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TricycleTheme {
                val navController = rememberNavController()
                Scaffold(
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