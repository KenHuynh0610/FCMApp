package com.example.fcmapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import layout.NotificationData
import layout.PushNotification
import layout.RetrofitInstance

const val TOPIC = "/topics/myTopic"

class MainActivity : AppCompatActivity() {
    val TAG = "mainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        buttonSend.setOnClickListener {
            val title = editTextTitle.text.toString()
            val message = editTextMessage.text.toString()
            if (title.isNotEmpty() && message.isNotEmpty()) {
                PushNotification(NotificationData(title, message), TOPIC).also {
                    sendNotification(it)
                }
            }
        }
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {

            val response =
                RetrofitInstance.api.postNotification(notification)
            Log.d(TAG, "sendNotification: ${response.toString()}")
            if(response.isSuccessful){
                Log.d(TAG, "sendNotification: sucessful")

            }


        }
}