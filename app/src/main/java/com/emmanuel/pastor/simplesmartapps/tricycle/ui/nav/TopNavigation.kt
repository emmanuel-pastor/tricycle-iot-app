package com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emmanuel.pastor.simplesmartapps.tricycle.ConnectionState
import com.emmanuel.pastor.simplesmartapps.tricycle.R
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.success

@Composable
fun TopNavigation(connectionState: ConnectionState, onReconnectClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.weight(1f)) {
            val indicatorColor = if (connectionState.isConnected) MaterialTheme.colorScheme.success else MaterialTheme.colorScheme.error
            Canvas(onDraw = {
                drawCircle(color = indicatorColor)
            }, modifier = Modifier.size(12.dp))
            if (connectionState.isConnected) {
                Text(
                    text = stringResource(R.string.tricycle_connected),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else if (connectionState.isLoading) {
                CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(28.dp))
            } else {
                FilledTonalButton(
                    onClick = onReconnectClicked,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = stringResource(R.string.reconnect),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = stringResource(R.string.reconnect),
                            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }
        IconButton(
            onClick = { },
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = stringResource(R.string.settings),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}