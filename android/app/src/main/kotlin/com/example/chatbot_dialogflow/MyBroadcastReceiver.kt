package com.example.chatbot_dialogflow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "MyBroadcastReceiver"

    private val PLAN_ROUTE = "edu.upc.gessi.broadcast.OSMAND.PLAN_ROUTE";

    private val PLAN_ROUTE_NAME = "name";
    private val PLAN_ROUTE_LATITUDE = "init_lat";
    private val PLAN_ROUTE_LONGITUDE = "init_long";



    override fun onReceive(context: Context, intent: Intent) {

        //TODO: maybe at some point https://developer.android.com/guide/components/broadcasts#context-registered-receivers

        if (intent.action.equals(PLAN_ROUTE)) {
            Log.d(TAG, "Hello! We did it! "+ intent.action)
            Log.d(TAG, "Name: " + intent.getStringExtra("name"))
            Log.d(TAG, "Latitude: " + intent.getDoubleExtra("init_lat", 0.0).toString())
            Log.d(TAG, "Longitude:" + intent.getDoubleExtra("init_long", 0.0).toString())
        } else  {
            Log.d(TAG, "Oh, something went wrong: " + intent.action);
        }

    }

}
