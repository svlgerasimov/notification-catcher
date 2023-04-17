package com.github.svlgerasimov.notificationcatcher.storage

import android.app.Notification
import android.service.notification.StatusBarNotification
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

fun mapNotificationToEntity(statusBarNotification: StatusBarNotification): NotificationEntity {
    return NotificationEntity(
        createdAt = LocalDateTime.now(),
        packageName = statusBarNotification.packageName,
        notificationText = statusBarNotification.notification
            ?.extras?.getString(Notification.EXTRA_TEXT)
    )
}

@Composable
fun NotificationFromEntity(notification: NotificationEntity) {
    val paddingModifier = Modifier.padding(10.dp)
    Card(
        modifier = paddingModifier
    ) {
        Column(modifier = paddingModifier) {
            Text(text = Converters().fromLocalDateTime(notification.createdAt) ?: "")
            Text(text = notification.packageName ?: "")
            Text(text = notification.notificationText ?: "")
        }
    }
}