package com.example.chatbot_dialogflow

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private val CHANNEL = "edu.upc.gessi.broadcast.CHANNEL"

    private val BROADCAST_COMMUNICATION = "edu.upc.gessi.broadcast";

    private val PLAN_ROUTE = "edu.upc.gessi.broadcast.OSMAND.PLAN_ROUTE";

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        flutterContext = flutterEngine

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->

            if (call.method == "registerBroadcasts") {

                //Register Broadcasts
                registerBroadcasts()

            }

        }
    }

    private fun registerBroadcasts(): Int {
        val br: BroadcastReceiver = MyBroadcastReceiver()

        val filter = IntentFilter(BROADCAST_COMMUNICATION).apply {
            addAction(PLAN_ROUTE);
            //TODO add new listeners from source apps
        }
        registerReceiver(br, filter)

        return 1
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
