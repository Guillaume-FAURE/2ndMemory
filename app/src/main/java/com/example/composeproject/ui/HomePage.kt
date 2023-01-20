package com.example.composeproject.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import com.example.composeproject.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeViewModel: HomeViewModel by viewModels()

        NotificationUtils(this).createNotificationChannel()
        NotificationUtils(this).scheduleNotification()
        setContent {
            NavControllerPage(homeViewModel)
            when {
                intent?.action == Intent.ACTION_SEND -> {
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let{
                        Log.d("Test","$it")
                        Toast
                            .makeText(this, "Received text: $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}


