package com.github.svlgerasimov.notificationcatcher.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "notifications")
data class NotificationEntity (
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime?,
    @ColumnInfo(name = "package_name") val packageName: String?,
    @ColumnInfo(name = "notification_text") val notificationText: String?
    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    override fun toString(): String {
        return "NotificationEntity(" +
                "createdAt=$createdAt, " +
                "id=$id, " +
                "packageName=$packageName, " +
                "notificationText=$notificationText)"
    }
}