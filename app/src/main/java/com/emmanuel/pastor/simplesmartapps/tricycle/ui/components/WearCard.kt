package com.emmanuel.pastor.simplesmartapps.tricycle.ui.components

import android.content.res.Configuration
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emmanuel.pastor.simplesmartapps.measurements.DistanceUnit
import com.emmanuel.pastor.simplesmartapps.measurements.Measure
import com.emmanuel.pastor.simplesmartapps.measurements.ProportionUnit
import com.emmanuel.pastor.simplesmartapps.tricycle.R
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.TricycleTheme
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.onSurfaceDiscrete
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.success
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme.warning

@Composable
fun WearCard(modifier: Modifier = Modifier, name: String, @DrawableRes icon: Int, percentage: Measure.Proportion, replaceIn: Measure.Distance) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.Start, modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Image(
                    imageVector = ImageVector.vectorResource(id = icon),
                    contentDescription = "$name disposable icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.size(20.dp)
                )
                Text(text = name, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary))
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.Start) {
                LinearProgressIndicator(
                    percentage.value.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clip(RoundedCornerShape(40.dp)),
                    color = percentage.value.let {
                        Log.d("WearCard", "percentage: $it")
                        when (it) {
                            in 0.0..0.799 -> MaterialTheme.colorScheme.success
                            in 0.800..0.949 -> MaterialTheme.colorScheme.warning
                            else -> MaterialTheme.colorScheme.error
                        }
                    },
                    trackColor = percentage.value.let {
                        when (it) {
                            in 0.0..79.9 -> MaterialTheme.colorScheme.success.copy(0.15f)
                            in 80.0..94.9 -> MaterialTheme.colorScheme.warning.copy(0.15f)
                            else -> MaterialTheme.colorScheme.error.copy(0.15f)
                        }
                    }
                )
                Text(
                    text = "Replace in ${replaceIn.toL10nString()}",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceDiscrete)
                )
            }
        }
    }
}

@Composable
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun WearCardPreview() {
    TricycleTheme {
        Box(Modifier.padding(8.dp)) {
            WearCard(
                name = "Tires",
                icon = R.drawable.ic_tires,
                percentage = Measure.Proportion(0.15, ProportionUnit.Percent),
                replaceIn = Measure.Distance(1000, DistanceUnit.Kilometer)
            )
        }
    }
}