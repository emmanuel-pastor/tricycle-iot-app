package com.emmanuel.pastor.simplesmartapps.tricycle

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav.BottomNavigation
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav.NavigationGraph
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav.TopNavigation
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.TricycleTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TricycleTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = hiltViewModel()
                val tricycleServicePermissions = rememberMultiplePermissionsState(getTricycleServicePermissions())
                val connectionState by viewModel.connectionState.collectAsState()

                Scaffold(
                    topBar = {
                        TopNavigation(connectionState = connectionState) {
                            if (tricycleServicePermissions.allPermissionsGranted) {
                                viewModel.onReconnectClicked()
                            } else {
                                tricycleServicePermissions.launchMultiplePermissionRequest()
                            }
                        }
                    },
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

    private fun getTricycleServicePermissions() = mutableListOf<String>().apply {
        // Bluetooth permissions
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            add(Manifest.permission.BLUETOOTH_ADMIN)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            add(Manifest.permission.BLUETOOTH_CONNECT)
            add(Manifest.permission.BLUETOOTH_SCAN)
        }

        // Notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }.toList()
}