package com.example.composeproject.ui.art

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeproject.R
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.home.FirstNavBar
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.ui.theme.backgroundSecondNavBar
import com.example.composeproject.viewmodel.HomeViewModelAbstract

@Composable
fun ArtScreen(
    art: ArtEntity,
    onClickClose: () -> Unit,
    homeViewModel: HomeViewModelAbstract,
){
    val textState = rememberSaveable {
        mutableStateOf(art.title)
    }
    Scaffold(topBar = {
        Column {
            FirstNavBar()
            TopAppBar(
                modifier = Modifier
                    .height(72.dp)
                ,
                backgroundColor = MaterialTheme.colors.backgroundSecondNavBar,
                contentColor = Color.White,
                title = {
                    Text(
                        text = "Editing",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                },
                actions = {
                    IconButton(onClick = {
                        art.let {
                            homeViewModel.addOrUpdateArt(it.copy(title = textState.value))
                            onClickClose()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = stringResource(id = R.string.home_screen_popup_button_save)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick =
                    {
                        onClickClose()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.home_screen_popup_button_dismiss)
                        )
                    }
                }
            )
        }
    }) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.backgroundColor)
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            textState.value?.let { it1 ->
                TextField(
                    value = it1,
                    onValueChange = { txt ->
                        textState.value = txt
                    },
                    modifier = Modifier
                        .background(Color.Gray)
                        .padding(10.dp),
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(1f)
            ) {

            }
        }
    }
}