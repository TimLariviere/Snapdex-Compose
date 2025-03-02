package com.kanoyatech.snapdex.ui.pokemon_details

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.Poppins
import com.kanoyatech.snapdex.domain.kg
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
                tint = Color(0xFF5B5B5B),
                modifier = Modifier
                    .size(16.dp)
            )

            Text(
                text = name.uppercase(),
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,

                color = Color(0xFF5B5B5B)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFE1E1E1),
                    shape = RoundedCornerShape(15)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DataCardItemPreview() {
    AppTheme {
        DataCardItem(
            icon = Icons.Weight,
            name = stringResource(id = R.string.weight),
            value = 100.0.kg.formatted()
        )
    }
}