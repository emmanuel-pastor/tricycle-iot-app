package com.emmanuel.pastor.simplesmartapps.tricycle.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emmanuel.pastor.simplesmartapps.measurements.MeasurementUnit
import com.emmanuel.pastor.simplesmartapps.measurements.ProportionUnit
import com.emmanuel.pastor.simplesmartapps.tricycle.R
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.TricycleTheme
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.onSurfaceDiscrete

@Composable
fun SensorCard(modifier: Modifier = Modifier, @DrawableRes icon: Int, name: String, value: Number?, unit: MeasurementUnit) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.Start, modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = ImageVector.vectorResource(id = icon),
                    contentDescription = "$name sensor icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Text(text = name, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = value?.toString() ?: "_",
                    style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier.alignByBaseline()
                )
                Text(
                    text = unit.symbol,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onSurfaceDiscrete),
                    modifier = modifier.alignByBaseline()
                )
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SensorCardPreview() {
    TricycleTheme {
        Box(Modifier.padding(8.dp)) {
            SensorCard(icon = R.drawable.ic_battery_5_7, name = "Battery", value = 75, unit = ProportionUnit.Percent)
        }
    }
}