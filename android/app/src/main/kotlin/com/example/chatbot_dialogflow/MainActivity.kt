package com.example.chatbot_dialogflow

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private val CHANNEL = "edu.upc.gessi.broadcast.CHANNEL"
    private val BROADCAST = "edu.upc.gessi.broadcast.TEST_BROADCAST"
    private val BROADCAST_INTENT = "edu.upc.gessi.broadcast.TEST_BROADCAST.intent"

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

        val filter = IntentFilter(BROADCAST).apply {
            addAction(BROADCAST_INTENT)
        }
        registerReceiver(br, filter)

        return 1
    }


}
