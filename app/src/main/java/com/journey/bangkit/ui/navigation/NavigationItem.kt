package com.journey.bangkit.ui.navigation

import android.os.Parcelable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class NavigationItem(
    val title: Int,
    val icon: @RawValue ImageVector,
    val screen: @RawValue Screen,
    val contentDescription: Int
) : Parcelable