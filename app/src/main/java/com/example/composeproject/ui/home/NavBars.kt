package com.example.composeproject.ui.home

import ConnectionLiveData
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeproject.R
import com.example.composeproject.ui.art.isOnline
import com.example.composeproject.ui.theme.backgroundFirstNavBar
import com.example.composeproject.ui.theme.backgroundSecondNavBar

lateinit var connectionLiveData: ConnectionLiveData

@Composable
fun FirstNavBar(){
    val context = LocalContext.current
    val online = remember {
        mutableStateOf(true)
    }
    connectionLiveData=ConnectionLiveData(context)
    val isOnline = connectionLiveData.observeAsState()

    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.backgroundFirstNavBar)
            .fillMaxWidth(1f)
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.brainwhite),
            contentDescription = "logoAppBrainWhite",
            modifier = Modifier
                .fillMaxHeight(1f)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )
        Text(
            text = "2nd Memory",
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Default,
            letterSpacing = 2.sp,
            color = Color.White,
        )
        Spacer(
            modifier = Modifier
                .width(20.dp)
                .fillMaxHeight()
        )
        val wifiIcon = if(isOnline.value==true){
            Icons.Rounded.Wifi
        }
        else {
            Icons.Rounded.WifiOff
        }
        Icon(
            imageVector = wifiIcon,
            contentDescription = "WifiOn",
            tint = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        )
        Spacer(modifier = Modifier
            .fillMaxHeight()
        )
    }
}

@Composable
fun SecondNavBar(
    currentPage: Int,
    onClickHome: () -> Unit,
    onClickDone: () -> Unit,
    onClickToDo: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(MaterialTheme.colors.backgroundSecondNavBar)
            .fillMaxWidth(1f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = { onClickHome() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            border = null
        ) {
            (if (currentPage==1){
                Modifier
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(10)
                    )
                    .padding(10.dp)
            } else {Modifier.padding(10.dp)}).let {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = it
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        contentDescription = "Home",
                        tint = Color.White
                    )
                    Text(text = "Home",
                        style = if(currentPage==1){ TextStyle(textDecoration = TextDecoration.Underline)
                        } else {
                            TextStyle(textDecoration = TextDecoration.None)
                        },
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
        OutlinedButton(
            onClick = { onClickDone() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            border = null
        ) {
            (if (currentPage==2){
                Modifier
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(10)
                    )
                    .padding(10.dp)
            } else {Modifier.padding(10.dp)}).let {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = it
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = "Done",
                        tint = Color.White
                    )
                    Text(text = "Done",
                        style = if(currentPage==2){ TextStyle(textDecoration = TextDecoration.Underline)
                        } else {
                            TextStyle(textDecoration = TextDecoration.None)
                        },
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
        OutlinedButton(
            onClick = { onClickToDo() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            border = null
        ) {
            (if (currentPage==3){
                Modifier
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(10)
                    )
                    .padding(10.dp)
            } else {Modifier.padding(10.dp)}).let {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = it
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddTask,
                        contentDescription = "ToDo",
                        tint = Color.White
                    )
                    Text(
                        text = "To Do",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}