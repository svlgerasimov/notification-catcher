package com.github.svlgerasimov.notificationcatcher.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotificationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDAO

    companion object {
        private var serviceInstance: AppDatabase? = null
        private var guiInstance: AppDatabase? = null
        private const val DB_NAME: String = "app_database"

        fun getServiceInstance(context: Context): AppDatabase {
            serviceInstance = buildIfNecessary(context, serviceInstance)
            return serviceInstance!!
        }

        fun getGuiInstance(context: Context): AppDatabase {
            guiInstance = buildIfNecessary(context, guiInstance)
            return guiInstance!!
        }

        private fun buildIfNecessary(context: Context, instance: AppDatabase?): AppDatabase {
            return instance ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            )
                .enableMultiInstanceInvalidation()
                .build()
        }
    }
}