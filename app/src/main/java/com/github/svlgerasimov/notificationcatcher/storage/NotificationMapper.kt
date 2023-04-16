package com.github.svlgerasimov.notificationcatcher.storage

import android.app.Notification
import android.service.notification.StatusBarNotification

fun mapNotificationToEntity(statusBarNotification: StatusBarNotification): NotificationEntity {
    return NotificationEntity(
        packageName = statusBarNotification.packageName,
        notificationText = statusBarNotification.notification
            ?.extras?.getString(Notification.EXTRA_TEXT)
    )
}