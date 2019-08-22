package com.example.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class BackgroundService: Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate() executed in thread " + Thread.currentThread().name)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand() executed in thread " + Thread.currentThread().name)

        // Perform Long Operations
        Thread.sleep(10000)

        return Service.START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind() executed in thread " + Thread.currentThread().name)
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // stopSelf()
        Log.i(TAG, "onDestroy() executed in thread " + Thread.currentThread().name)
    }

    companion object {
        private val TAG = BackgroundService::class.java.simpleName
    }
}
