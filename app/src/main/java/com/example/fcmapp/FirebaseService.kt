package com.example.fcmapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

const val CHANNEL_ID = "myChannel"
class FirebaseService : FirebaseMessagingService() {

    //whenever this device receive a message, this method will be called.
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)


        val intent = Intent(this, MainActivity::class.java)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //generate random ID for notification so they won't override one another so we'll have notification displays at the same time
        val notificationID = Random.nextInt()
        //clear all the activity and put MainActivity on top
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification  = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.ic_baseline_adb_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID, notification)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channel = NotificationChannel(CHANNEL_ID, "PushNotification", IMPORTANCE_HIGH).apply {
            description = ""
            enableLights(true)
            lightColor = Color.GREEN
        }
            notificationManager.createNotificationChannel(channel)
        }


    }
}