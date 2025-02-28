package com.kanoyatech.snapdex.ui.pokemon_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Poppins

@Composable
fun ElementTag(
    elementUi: ElementUi,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(67))
            .background(elementUi.color)
            .padding(horizontal = 14.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(28.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = elementUi.imageId),
                contentDescription = null,
                tint = elementUi.color,
                modifier = Modifier
                    .size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = elementUi.name),
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun ElementViewPreview() {
    AppTheme {
        ElementTag(
            elementUi = ElementUi.Fire
        )
    }
}