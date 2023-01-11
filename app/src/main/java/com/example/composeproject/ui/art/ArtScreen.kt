package com.example.composeproject.ui.art

import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.ui.home.FirstNavBar
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.viewmodel.HomeViewModelAbstract
import androidx.compose.ui.platform.LocalDensity
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.composeproject.data.remote.*
import com.example.composeproject.model.ArtInformationState

@Composable
fun ArtScreen(
    art: ArtEntity,
    onClickClose: () -> Unit,
    homeViewModel: HomeViewModelAbstract,
){

    val artInformationState = ArtInformationState(
        titleState = rememberSaveable { mutableStateOf(art.title) },
        typeState = rememberSaveable { mutableStateOf(art.type) },
        stateState = rememberSaveable { mutableStateOf(art.state) },
        authorState = rememberSaveable { mutableStateOf(art.author) },
        descriptionState = rememberSaveable { mutableStateOf(art.description) },
        markState = rememberSaveable { mutableStateOf(art.mark) },
        pictureState = rememberSaveable { mutableStateOf(art.picture) },
    )
    Scaffold(
        modifier = Modifier
            .verticalScroll(rememberScrollState(0))
            .heightIn(min = 800.dp, max = 3000.dp),
        topBar = {
        Column {
            FirstNavBar()
            TopAppBarArtScreen(
                artInformationState = artInformationState,
                art = art,
                onClickClose = onClickClose,
                homeViewModel = homeViewModel
            )
        }
    }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.backgroundColor)
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            TypeDropDownMenu(artInformationState.typeState)
            StateDropDownMenu(artInformationState.stateState)
            APISearchAnime(artInformationState)
            RowTitleInputState(title = "Title : ",
                state = artInformationState.titleState)
            RowTitleInputState(title = "Author : ",
                state = artInformationState.authorState)
            RowTitleInputState(title = "Description : ",
                state = artInformationState.descriptionState)
            RowTitleInputState(title = "Picture : ",
                state = artInformationState.pictureState)
            RowTitleInputState(title = "Mark : ",
                state = artInformationState.markState)
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
        whiteMiddleText(string = "Type : ")
        Column {
            Box {
                typeState.value?.let {
                    OutlinedTextField(
                        value = stateHolder.value,
                        onValueChange = {},
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
                            typeState.value = stateHolder.value
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
        whiteMiddleText(string = "State : ")
        Column {
            Box {
                stateState.value?.let {
                    OutlinedTextField(
                        value = stateHolder.value,
                        onValueChange = {},
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
                            stateState.value = stateHolder.value
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
fun APISearchAnime(
    artState: ArtInformationState
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val listArt = remember { mutableStateListOf(ArtDataDto(
            id=0,
            title="",
            pictureURL = ""
            ))
        }
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(15.dp))

        artState.titleState.value?.let { it ->
            TextField(
                value = it,
                onValueChange = { artState.titleState.value = it },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .background(MaterialTheme.colors.backgroundColor)
                    .border(2.dp, Color.White)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    when(artState.typeState.value){
                        "Anime"-> {
                            val data = artState.titleState.value?.let {
                                getAnimeByString(
                                    string = it,
                                    listAnime = listArt
                                )
                            }
                        }
                        "Manga"->{
                            val data = artState.titleState.value?.let {
                                getMangaByString(
                                    string = it,
                                    listManga = listArt
                                )
                            }
                        }
                        else->{
                            Log.d("APICall", "Else")
                            Toast
                                .makeText(
                                    context,
                                    "Please, select a type to use the right Database",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                }
            ) {
                Text(text = "Get Data")
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            if (!listArt.isEmpty() && listArt[0].id!=0){
                listArt.forEach{
                        animeDataDto ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                            .border(1.dp, Color.White)
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource(),
                                onClick = {
                                    Log.d("APICall", "Initialization")
                                    when (artState.typeState.value){
                                        "Anime" -> {
                                            Log.d("APICall", "Anime")
                                            getInformationFromAnime(
                                                animeDataDto.id,
                                                artState
                                            )
                                        }
                                        "Manga"-> {
                                            Log.d("APICall", "Manga")
                                            getInformationFromManga(
                                                animeDataDto.id,
                                                artState
                                            )
                                        }
                                    }
                                    listArt.removeAll(listArt)
                                }
                            )
                    ){
                        AsyncImage(
                            model = animeDataDto.pictureURL,
                            contentDescription = "imageAnime",
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = animeDataDto.title,
                            color = Color.White,
                            modifier = Modifier
                                .defaultMinSize(100.dp)
                                .padding(10.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}