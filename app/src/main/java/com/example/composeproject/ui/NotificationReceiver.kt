package com.example.composeproject.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Receive", "Initialize")
        NotificationUtils(context).createNotification()
    }
}