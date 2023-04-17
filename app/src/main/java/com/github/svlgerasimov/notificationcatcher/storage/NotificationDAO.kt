package com.github.svlgerasimov.notificationcatcher.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationDAO {

    @Insert
    fun insert(notification: NotificationEntity)

    @Query("DELETE FROM notifications")
    fun deleteAll()

    @Query("SELECT n.id, n.created_at, n.package_name, n.notification_text " +
            "FROM notifications n")
    fun findAll(): List<NotificationEntity>
}