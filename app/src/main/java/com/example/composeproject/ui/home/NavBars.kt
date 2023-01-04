package com.example.composeproject.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.composeproject.ui.theme.backgroundFirstNavBar
import com.example.composeproject.ui.theme.backgroundSecondNavBar

@Composable
fun FirstNavBar(){
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.backgroundFirstNavBar)
            .fillMaxWidth(1f)
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.brain),
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
            letterSpacing = 2.sp
        )
        OutlinedButton(onClick =
        { /* context.startActivity(Intent(context, ParameterPage::class.java)) */ },
            modifier = Modifier
                .padding(10.dp)
                .shadow(0.dp)
                .fillMaxHeight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            border = BorderStroke(0.dp, Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.parameter),
                contentDescription = "parameterWhite",
                modifier = Modifier.fillMaxHeight(1f)
            )
        }
    }
}

@Composable
fun SecondNavBar(currentPage: Int){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(MaterialTheme.colors.backgroundSecondNavBar)
            .fillMaxWidth(1f)
            .height(72.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .shadow(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            )
        ) {
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
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .shadow(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            )
        ) {
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
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .shadow(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            )
        ) {
            Text(text = "To Do",
                style = if(currentPage==3){ TextStyle(textDecoration = TextDecoration.Underline)
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