package com.lambao.mrbeast.presentation.service

import android.app.Service
import android.content.Intent
import com.lambao.base.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartedService : Service() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onBind(p0: Intent?) = null

    override fun onCreate() {
        super.onCreate()
        log("SERVICE IS CREATED")
        scope.launch {
            repeat(10) {
                delay(1000)
                log("StartedService","count: $it")
                if (it == 9) stopSelf()
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("SERVICE IS RUNNING")

        return START_NOT_STICKY
    }


    override fun onDestroy() {
        log("SERVICE IS KILLED")
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

    }
}