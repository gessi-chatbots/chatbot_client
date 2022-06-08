package com.example.chatbot_dialogflow

import android.content.BroadcastReceiver
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

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->

            if (call.method == "registerBroadcasts") {
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


}
