package com.example.composeproject.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.composeproject.R


class NotificationUtils(private val context: Context) {

    private val notificationChannelId = "uncreatedContentDelay"
    private val notificationChannelName = "uncreated content delay"
    private val notificationChannelDescription =
        "This channel id is dedicated to the notification" + "that attract the user to use the app if it is not used for a long time"
    private val notificationChannelImportance = NotificationManager.IMPORTANCE_DEFAULT
    private val notificationId = 1
    private val notificationTitle = "Add something new !"
    private val notificationMessage =
        "It's been a while that you didn't had something in the app, update your manga and anime !!"


    private val INTERVAL_MS = 30 * 1000 // 30 secondes

    fun createNotificationChannel() {
        val channel = NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            notificationChannelImportance
        )
        channel.description = notificationChannelDescription

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                 as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotification() {
        Log.d("Notification", "Creation")
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create an Intent for the activity you want to start
        val resultIntent = Intent(context, HomePage::class.java)
        // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(
                notificationId, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setChannelId(notificationChannelId)
            .build()

        manager.notify(notificationId, notification)
    }

    fun scheduleNotification() {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        Log.d("Schedule", "Initialize")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val futureInMillis = SystemClock.elapsedRealtime() + INTERVAL_MS

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            futureInMillis,
            pendingIntent
        )
    }
}