package com.example.composeproject.ui.art

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeproject.R
import com.example.composeproject.model.ArtEntity
import com.example.composeproject.model.ArtInformationState
import com.example.composeproject.ui.theme.backgroundColor
import com.example.composeproject.ui.theme.backgroundSecondNavBar
import com.example.composeproject.viewmodel.HomeViewModelAbstract

@Composable
fun whiteMiddleText(string: String){
    Text(
        text = string,
        color = Color.White,
        modifier = Modifier
            .defaultMinSize(100.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun inputToState(state: MutableState<String?>){
    state.value?.let { it1 ->
        TextField(
            value = it1,
            onValueChange = { txt ->
                state.value = txt
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

@Composable
fun RowTitleInputState(
    title: String,
    state: MutableState<String?>
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .heightIn(min = 50.dp, max = 100.dp)
    ) {
        whiteMiddleText(string = title)
        inputToState(state = state)
    }
}

@Composable
fun TopAppBarArtScreen(
    artInformationState: ArtInformationState,
    art: ArtEntity,
    onClickClose: () -> Unit,
    homeViewModel: HomeViewModelAbstract,
){
    val context = LocalContext.current
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
            )
        },
        actions = {
            IconButton(onClick = {
                art.let {
                    if (
                        artInformationState.authorState.value != "" &&
                        artInformationState.titleState.value != "" &&
                        artInformationState.descriptionState.value != "" &&
                        artInformationState.markState.value != null &&
                        artInformationState.typeState.value != "" &&
                        artInformationState.stateState.value != "" &&
                        artInformationState.pictureState.value != ""
                    ){
                        homeViewModel.addOrUpdateArt(
                            it.copy(
                                author = artInformationState.authorState.value,
                                title = artInformationState.titleState.value,
                                description = artInformationState.descriptionState.value,
                                mark = artInformationState.markState.value,
                                type = artInformationState.typeState.value,
                                state = artInformationState.stateState.value,
                                picture = artInformationState.pictureState.value,
                            ))
                        onClickClose()
                    }
                    else {
                        Toast
                            .makeText(context, "Not enough information", Toast.LENGTH_LONG)
                            .show()
                    }
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

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}