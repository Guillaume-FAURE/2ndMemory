package com.example.composeproject.ui.art

import android.util.Log
import androidx.compose.foundation.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import coil.compose.AsyncImage
import com.example.composeproject.data.remote.AnimeDataDto
import com.example.composeproject.data.remote.AnimeNode
import com.example.composeproject.data.remote.SearchAnimeApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Json
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    var animeSearch: List<AnimeDataDto>
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
            APISearchAnime()
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
fun APISearchAnime(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val id = remember { mutableStateOf(TextFieldValue()) }

        val listAnime = remember { mutableStateListOf<AnimeDataDto>(AnimeDataDto(
            id=0,
            title="",
            pictureURL = ""
        ))
        }

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = id.value,
            onValueChange = { id.value = it },
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = Color.White
            ),
            modifier = Modifier
                .background(MaterialTheme.colors.backgroundColor)
                .border(2.dp, Color.White)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    val data = sendRequest(
                        id = id.value.text,
                        listAnime = listAnime
                    )
                    Log.d("Main Activity", listAnime.toString())
                }
            ) {
                Text(text = "Get Data")
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
        ) {
            if (listAnime[0].id!=0){
                listAnime.forEach{
                        animeDataDto ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                            .border(1.dp, Color.White)
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource(),
                                onClick = {
                                    getInformationFromAnime(animeDataDto.id)
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

fun sendRequest(
    id: String,
    listAnime: MutableList<AnimeDataDto>
) {
    Log.d("SendRequest", "Initialization")
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.myanimelist.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    Log.d("SendRequest", "APIBuild")
    val api = retrofit.create(SearchAnimeApi::class.java)

    val call: Call<ResponseBody> = api.getAnimeId(id);
    Log.d("SendRequest", "Before call")
    if (call != null) {
        Log.d("SendRequest", "call != null")
        call.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    // Log.d("SendRequest", "Response Received : " + (response.body()?.string() ?: "Error"))
                    val sType = object : TypeToken<List<AnimeNode>>() { }.type
                    val animeJson = JSONObject(response.body()?.string()).getJSONArray("data").toString()
                    if (animeJson != null) {
                        Log.d("SendRequest", "JSON :$animeJson")
                    }
                    val animeArray = Gson().fromJson<List<AnimeNode>>(animeJson, sType)
                    Log.d("SendRequest", "AnimeArray : $animeArray")
                    listAnime.removeAll(listAnime)
                    animeArray.forEach{
                        anime -> listAnime.add(AnimeDataDto(
                                id = anime.node.id,
                                title = anime.node.title,
                                pictureURL = anime.node.main_picture.medium
                            ))
                    }
                    Log.d("SendRequest", "Array : $listAnime")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Main", "Failed mate " + t.message.toString())
            }
        })
    }
    else {
        Log.d("SendRequest", "call = null")
    }
}

fun getInformationFromAnime(id: Int){
    
}