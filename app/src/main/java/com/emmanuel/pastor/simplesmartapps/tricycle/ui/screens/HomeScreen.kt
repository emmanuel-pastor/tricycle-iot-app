package com.emmanuel.pastor.simplesmartapps.tricycle.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Home Screen", style = MaterialTheme.typography.displayMedium, modifier = Modifier.align(Center))
    }
}