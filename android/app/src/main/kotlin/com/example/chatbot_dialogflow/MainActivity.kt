package com.example.chatbot_dialogflow

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private val TAG = "MainActivity"

    private val CHANNEL = "edu.upc.gessi.broadcast.CHANNEL"

    private val BROADCAST_COMMUNICATION = "edu.upc.gessi.broadcast";

    private val PLAN_ROUTE = "edu.upc.gessi.broadcast.OSMAND.PLAN_ROUTE";
    private val CREATE_EVENT = "edu.upc.gessi.broadcast.ETAR.CREATE_EVENT";

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        flutterContext = flutterEngine

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->

            if (call.method == "registerBroadcasts") {

                //Register Broadcasts
                registerBroadcasts()

            } else if (call.method == "sendBroadcastIntent") {

                //Send Broadcast Intent
                sendBroadcastIntent(call.arguments())
            }

        }
    }

    private fun registerBroadcasts() {
        val br: BroadcastReceiver = MyBroadcastReceiver()

        val filter = IntentFilter(BROADCAST_COMMUNICATION).apply {
            addAction(PLAN_ROUTE);
            //TODO add new listeners from source apps
        }
        registerReceiver(br, filter)

        return
    }

    private fun sendBroadcastIntent(argument : Map<String, String> ) {
        Log.d(TAG, "Name: "+ argument["name"])
        Log.d(TAG, "Description: " + argument["description"])
        Log.d(TAG, "Invites: " + argument["invites"])
        Log.d(TAG, "Start date/time: " + argument["start_date_time"])
        Log.d(TAG, "End date/time: " + argument["end_date_time"])
        Log.d(TAG, "Latitude: " + argument["latitude"])
        Log.d(TAG, "Longitude: " + argument["longitude"])

        Intent().also { intent ->
            intent.action = CREATE_EVENT
            intent.putExtra("name", argument["name"])
            intent.putExtra("description", argument["description"])
            intent.putExtra("invites", argument["invites"])
            intent.putExtra("start_date_time", argument["start_date_time"])
            intent.putExtra("end_date_time", argument["end_date_time"])
            intent.putExtra("latitude", argument["latitude"])
            intent.putExtra("longitude", argument["longitude"])
            sendBroadcast(intent)
        }

        Log.d(TAG, "Broadcast intent $CREATE_EVENT has been sent!")
    }

    companion object {
        private val REVERSE_CHANNEL = "edu.upc.gessi.broadcast.REVERSE_CHANNEL";
        private lateinit var flutterContext : FlutterEngine

        fun fromPlanRouteToCreateEvent(name: String, latitude: String, longitude: String) {
            MethodChannel(flutterContext.dartExecutor.binaryMessenger, REVERSE_CHANNEL)
                    .invokeMethod("fromPlanRouteToCreateEvent",
                            mapOf("name" to name,
                            "latitude" to latitude,
                            "longitude" to longitude))
        }

    }

}
