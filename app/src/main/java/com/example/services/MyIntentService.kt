package com.example.services

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log

class IntentService: IntentService("MyIntentServiceThread") {

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate executed in " + Thread.currentThread().name)
    }

    override fun onHandleIntent(intent: Intent?) {

        Log.i(TAG, "onHandleIntent executed in " + Thread.currentThread().name)

        // Long Running operations
        var counter = 0
        while (counter <= 10) {
            Log.i(TAG, "counter $counter")
            Thread.sleep(1000)
            counter++
        }

        // Using ResultReceiver to send data and communicate
//        sendResultResultReceiver(intent, counter)

        sendResultBroadcastReceiver(counter)

    }

    private fun sendResultBroadcastReceiver(counter: Int) {

        val resultIntent = Intent("action.service.broadcast.receive")
        resultIntent.putExtra("br_result", "Broadcast receiver $counter")
        sendBroadcast(resultIntent)
    }

    private fun sendResultResultReceiver(intent: Intent?, counter: Int) {

        val mResultReceiver = intent?.getParcelableExtra<ResultReceiver>("intent_result_receiver")

        // Send data from the service
        var bundle = Bundle()
        bundle.putString("rr_result", "Result Receiver: $counter")
        mResultReceiver?.send(15, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy executed in " + Thread.currentThread().name)
    }

    companion object {
        private val TAG = IntentService::class.java.simpleName
    }
}
