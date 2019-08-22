package com.example.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startBackgroundService(view: View) {
        val intent = Intent(this, BackgroundService::class.java)
        startService(intent)
    }

    fun stopBackgroundService(view: View) {
        val intent = Intent(this, BackgroundService::class.java)
        stopService(intent)
    }

    fun startIntentService(view: View) {

        val ResultReceiver = IntentResultReceiver(null)

        val intent = Intent(this, IntentService::class.java)
        intent.putExtra("intent_result_receiver", ResultReceiver)
        startService(intent)
    }

    fun startForegroundService(view: View) {
        val intent = Intent(this, ForegroundService::class.java)
        intent.action = ForegroundService.ACTION_START_FOREGROUND_SERVICE
        startService(intent)
    }

    fun stopForegroundService(view: View) {
        val intent = Intent(this, ForegroundService::class.java)
        intent.action = ForegroundService.ACTION_STOP_FOREGROUND_SERVICE
        startService(intent)
    }

    inner class IntentResultReceiver(handler: Handler?): ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            if (resultCode == 15 && resultData != null) {
                val result = resultData.getString("rr_result")

                Handler.post {
                    tvIntentServiceResult.text = result
                }
            }

            super.onReceiveResult(resultCode, resultData)
        }
    }

    val BroadcastReceiver = object: BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val result = intent?.getStringExtra("br_result")
            Handler.post {
                tvIntentServiceResult.text = result
            }
        }

    }

    override fun onResume() {
        super.onResume()

        // Registering BroadcastReceiver
        val IntentFilter = IntentFilter()
        IntentFilter.addAction("action.service.broadcast.receive")
        registerReceiver(BroadcastReceiver, IntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(BroadcastReceiver)
    }

}
