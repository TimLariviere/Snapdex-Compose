package com.kanoyatech.snapdex.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

object Icons {
    val ArrowBack = Icons.AutoMirrored.Default.ArrowBack
    val Search = Icons.Filled.Search
    val Close = Icons.Filled.Close
    val Add = Icons.Filled.Add
    val Check = Icons.Filled.Check

    val ArrowDown: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_arrow_down)

    val App: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_app)

    val Category: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_category)

    val Eye: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_eye)

    val EyeClosed: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_eye_closed)

    val Filter: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_filter)

    val GridUnselected: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_grid_unselected)

    val GridSelected: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_grid_selected)

    val Height: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_height)

    val Pokeball: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_pokeball)

    val ProfileUnselected: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_profile_unselected)

    val ProfileSelected: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_profile_selected)

    val StatsUnselected: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_stats_unselected)

    val StatsSelected: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_stats_selected)

    val Weight: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_weight)

    val Male: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_male)

    val Female: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_female)
}