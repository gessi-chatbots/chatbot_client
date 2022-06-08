package com.example.chatbot_dialogflow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "MyBroadcastReceiver"
    private val BROADCAST_INTENT = "edu.upc.gessi.broadcast.TEST_BROADCAST.intent"


    override fun onReceive(context: Context, intent: Intent) {

        //TODO: maybe at some point https://developer.android.com/guide/components/broadcasts#context-registered-receivers

        Log.d(TAG, intent.action)

    }

}
