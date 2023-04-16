package com.github.svlgerasimov.notificationcatcher

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.github.svlgerasimov.notificationcatcher.storage.AppDatabase
import com.github.svlgerasimov.notificationcatcher.storage.mapNotificationToEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotificationsService : NotificationListenerService() {

    companion object {
        private const val TAG: String = "NotificationService"
    }

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        job.cancel()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val notification = sbn?.notification
        val text = notification?.extras?.getString(Notification.EXTRA_TEXT)
        Log.d(TAG, "Notification from ${sbn?.packageName}: $text")


        if (sbn != null) {
            scope.launch {
                AppDatabase.getServiceInstance(applicationContext).notificationDao()
                    .insert(mapNotificationToEntity(sbn))
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        Log.d(TAG, "onNotificationRemoved: ${sbn?.packageName}")
    }
}