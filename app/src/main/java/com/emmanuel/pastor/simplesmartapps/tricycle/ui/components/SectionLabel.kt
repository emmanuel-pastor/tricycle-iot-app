package com.emmanuel.pastor.simplesmartapps.tricycle.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.onSurfaceOff

@Composable
fun SectionLabel(modifier: Modifier = Modifier, mainLabelText: String, secondaryLabelText: String? = null) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()) {
        Text(
            text = mainLabelText,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.alignByBaseline()
        )
        secondaryLabelText?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceOff),
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}