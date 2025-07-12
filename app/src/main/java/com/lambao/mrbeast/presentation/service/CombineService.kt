package com.lambao.mrbeast.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.lambao.base.utils.log
import com.lambao.mrbeast_anime.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CombineService : Service() {

    private val scope = CoroutineScope(Dispatchers.IO)

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "PersistentServiceChannel"
    }

    private val binder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
//        createNotificationChannel()
        scope.launch {
            repeat(10) {
                delay(1000)
                log("LAMNB", "count: $it")
                if (it == 9) {

                    stopSelf()
                }
            }
        }
        log("LAMNB", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("LAMNB", "Service started")
//        val notification = createNotification()
////
//        // This makes it a foreground service
//        startForeground(NOTIFICATION_ID, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        log("LAMNB", "Service bound")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        log("LAMNB", "Service unbound")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        log("LAMNB", "Service destroyed")
        scope.cancel()
        super.onDestroy()
    }

    inner class LocalBinder : Binder() {
        fun getService(): CombineService = this@CombineService
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Persistent Service",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Keeps service running in background"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("App Running")
            .setContentText("Service is running in background")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }
}