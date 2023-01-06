package com.example.composeproject.ui.art

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector

class ExposedDropMenuStakeHolder(items: List<String>) {

    var enabled by mutableStateOf(false)
    var value by mutableStateOf("")
    var selectedIndex by mutableStateOf(-1)
    var size by mutableStateOf(Size.Zero)
    var items: List<String>
    val icon: ImageVector
    @Composable get() = if (enabled){
        Icons.Default.ArrowDropDown
    }
    else {
        Icons.Default.ArrowDropUp
    }

    init {
        this.items =items
    }

    fun onEnabled(newValue: Boolean){
        enabled=newValue
    }
    fun onSelectedIndex(newIndex: Int){
        selectedIndex=newIndex
        value = items[selectedIndex]
    }
    fun onSize(newValue: Size){
        size=newValue
    }
}

@Composable
fun rememberExposedMenuStateHolder(items: List<String>) = remember(items){
    ExposedDropMenuStakeHolder(items)
}