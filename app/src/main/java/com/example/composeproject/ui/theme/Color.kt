package com.example.composeproject.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Colors.backgroundColor: Color
    get() = if (isLight) Color(0xFF252525) else Color(0xFF252525)
val Colors.backgroundFirstNavBar: Color
    get() = if (isLight) Color(0xFF000000) else Color(0xFF000000)
val Colors.backgroundSecondNavBar: Color
    get() = if (isLight) Color(0xFF3C3C3C) else Color(0xFF3C3C3C)