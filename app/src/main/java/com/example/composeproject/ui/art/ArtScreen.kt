package com.example.composeproject.ui.art

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.composeproject.R
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.home.FirstNavBar
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.ui.theme.backgroundSecondNavBar
import com.example.composeproject.viewmodel.HomeViewModelAbstract
import androidx.compose.ui.platform.LocalDensity

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
                            homeViewModel.addOrUpdateArt(
                                it.copy(
                                    title = textState.value,
                                    type = typeState.value,
                                    state = stateState.value
                                ))
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
                            color = Color.White
                        ),
                        modifier = Modifier
                            .background(MaterialTheme.colors.backgroundColor)
                            .border(2.dp, Color.White)
                    )
                }
            }
            TypeDropDownMenu(typeState)
            StateDropDownMenu(stateState)
        }
    }
}

@Composable
fun TypeDropDownMenu (typeState: MutableState<String?>){
    val typeItems = listOf(
        "Book",
        "Manga",
        "Anime",
        "Film"
    )
    val stateHolder = rememberExposedMenuStateHolder(typeItems)
    if (stateHolder.value == ""){
        stateHolder.value = typeState.value.toString()
    }
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
        Column {
            Box {
                typeState.value?.let {
                    OutlinedTextField(
                        value = stateHolder.value,
                        onValueChange = {
                            typeState.value = stateHolder.value
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = stateHolder.icon,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.clickable {
                                    stateHolder.onEnabled(!(stateHolder.enabled))
                                }
                            )
                        },
                        modifier = Modifier
                            .onGloballyPositioned {
                                stateHolder.onSize(it.size.toSize())
                            }
                            .background(MaterialTheme.colors.backgroundColor)
                            .border(2.dp, Color.White)
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource(),
                                onClick = {
                                    stateHolder.onEnabled(true)
                                }
                            ),
                        readOnly = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                        ),
                        enabled = false,
                    )
                }
            }
            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = {
                    stateHolder.onEnabled(false)
                },
                modifier = Modifier.width(with(LocalDensity.current){stateHolder.size.width.toDp()})
            ) {
                stateHolder.items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            stateHolder.onSelectedIndex(index)
                            stateHolder.onEnabled(false)
                        }
                    ) {
                        Text(text = s)
                    }
                }

            }
        }
    }
}

@Composable
fun StateDropDownMenu (stateState: MutableState<String?>) {
    val stateItems = listOf(
        "Done",
        "ToDo",
        "Current",
    )
    val stateHolder = rememberExposedMenuStateHolder(stateItems)
    if (stateHolder.value == ""){
        stateHolder.value = stateState.value.toString()
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ){
        Text(
            text = "State : ",
            color = Color.White,
            modifier = Modifier.defaultMinSize(100.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Column {
            Box {
                stateState.value?.let {
                    OutlinedTextField(
                        value = stateHolder.value,
                        onValueChange = {
                            stateState.value = stateHolder.value
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = stateHolder.icon,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.clickable {
                                    stateHolder.onEnabled(!(stateHolder.enabled))
                                }
                            )
                        },
                        modifier = Modifier
                            .onGloballyPositioned {
                                stateHolder.onSize(it.size.toSize())
                            }
                            .background(MaterialTheme.colors.backgroundColor)
                            .border(2.dp, Color.White)
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource(),
                                onClick = {
                                    stateHolder.onEnabled(true)
                                }
                            ),
                        readOnly = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                        ),
                        enabled = false
                    )
                }
            }
            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = {
                    stateHolder.onEnabled(false)
                },
                modifier = Modifier.width(with(LocalDensity.current){stateHolder.size.width.toDp()})
            ) {
                stateHolder.items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            stateHolder.onSelectedIndex(index)
                            stateHolder.onEnabled(false)
                        }
                    ) {
                        Text(text = s)
                    }
                }

            }
        }
    }
}