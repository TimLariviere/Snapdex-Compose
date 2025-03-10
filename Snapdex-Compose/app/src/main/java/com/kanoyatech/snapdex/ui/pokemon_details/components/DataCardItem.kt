package com.kanoyatech.snapdex.ui.pokemon_details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.theme.components.MaterialText
import com.kanoyatech.snapdex.ui.utils.formatted

@Composable
fun DataCardItem(
    icon: ImageVector,
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = DataCardItemColors.iconColor,
                modifier = Modifier
                    .size(16.dp)
            )

            MaterialText(
                text = name.uppercase(),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp,
                color = DataCardItemColors.titleColor
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = DataCardItemColors.boxColor,
                    shape = RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            MaterialText(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

object DataCardItemColors {
    val iconColor: Color @Composable get() = MaterialTheme.colorScheme.secondary
    val titleColor: Color @Composable get() = MaterialTheme.colorScheme.secondary
    val boxColor: Color @Composable get() = MaterialTheme.colorScheme.surface
}

@Preview(showBackground = true)
@Composable
private fun DataCardItemPreview() {
    AppTheme {
        DataCardItem(
            icon = Icons.Weight,
            name = stringResource(id = R.string.weight),
            value = 100.0.kg.formatted(),
            modifier = Modifier
                .padding(10.dp)
        )
    }
}