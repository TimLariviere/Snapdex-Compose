package com.kanoyatech.snapdex.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kanoyatech.snapdex.R

object Icons {
    val ArrowBack = Icons.AutoMirrored.Default.ArrowBack
    val Search = Icons.Filled.Search
    val Profile = Icons.Filled.AccountCircle
    val Close = Icons.Filled.Close
    val Add = Icons.Filled.Add
    val Check = Icons.Filled.Check

    val Apps: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_apps)

    val ArrowDown: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_arrow_down)

    val Category: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_category)

    val EvolutionArrow: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_evolution_arrow)

    val Height: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_height)

    val Pokeball: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_pokeball)

    val Statistics: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_statistics)

    val Weight: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_weight)

    val Filter: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_filter)

    val Eye: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_eye)

    val EyeClosed: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_eye_closed)
}