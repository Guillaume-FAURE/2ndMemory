package com.example.composeproject.ui.art

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
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
import com.example.composeproject.ui.theme.blackText
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
    val typeState = rememberSaveable {
        mutableStateOf(art.type)
    }
    val stateState = rememberSaveable {
        mutableStateOf(art.state)
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
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Title : ",
                    color = Color.White,
                    modifier = Modifier.defaultMinSize(100.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                textState.value?.let { it1 ->
                    TextField(
                        value = it1,
                        onValueChange = { txt ->
                            textState.value = txt
                        },
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            color = MaterialTheme.colors.blackText
                        ),
                        modifier = Modifier
                            .background(Color.White)
                    )
                }
            }
            // typeDropDownMenu(typeState)
            // stateDropDownMenu(stateState)
        }
    }
}

@Composable
fun typeDropDownMenu (typeState: MutableState<String?>){
    val typeItems = listOf<String>(
        "Book",
        "Manga",
        "Anime",
        "Film"
    )
    var selectedIndex by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Type : ",
            color = Color.White,
            modifier = Modifier.defaultMinSize(100.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        typeState.value?.let { it1 ->
            DropdownMenu(
                expanded = true,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
            ){
                typeItems.forEachIndexed{ index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) {
                        Text(text = s)
                    }
                }
            }
        }
    }
}

/* @Composable
fun stateDropDownMenu (stateState: State<String?>) {
    val stateItems = listOf<String>(
        "Done",
        "ToDo",
        "Current",
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "State : ",
            color = Color.White,
            modifier = Modifier.defaultMinSize(100.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        stateState.value?.let { it1 ->
            TextField(
                value = it1,
                onValueChange = { txt ->
                    stateState.value = txt
                },
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .background(Color.White)
            )
        }
    }
} */