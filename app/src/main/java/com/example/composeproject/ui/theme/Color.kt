package com.example.composeproject.ui.theme

import android.graphics.Color.parseColor
import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Colors.backgroundColor: Color
    get() = "#252525".color

val String.color
    get() = Color(parseColor(this))