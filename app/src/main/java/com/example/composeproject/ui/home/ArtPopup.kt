package com.example.composeproject.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.composeproject.R
import com.example.composeproject.ui.theme.backgroundColor

@Composable
fun ArtPopup(
    title: String = "",
    onClickSave: (String) -> Unit,
    onClickDismiss: () -> Unit,
){
    val textState = rememberSaveable {
        mutableStateOf(title)
    }
    Dialog(onDismissRequest = onClickDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.backgroundColor)
                .padding(10.dp)
        ) {
            BasicTextField(
                value = textState.value,
                onValueChange = {txt->
                    textState.value=txt
                },
                modifier = Modifier
                    .background(Color.Gray)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onClickDismiss() }) {
                    Text(text = stringResource(id = R.string.home_screen_popup_button_dismiss))
                }
                Button(onClick = { onClickSave(textState.value) }) {
                    Text(text = stringResource(id = R.string.home_screen_popup_button_save))
                }
            }
        }
    }
}