package com.github.svlgerasimov.notificationcatcher

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import com.github.svlgerasimov.notificationcatcher.storage.AppDatabase
import com.github.svlgerasimov.notificationcatcher.storage.NotificationEntity
import com.github.svlgerasimov.notificationcatcher.storage.NotificationFromEntity
import com.github.svlgerasimov.notificationcatcher.ui.theme.NotificationCatcherTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationCatcherTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Column {
                        val listText = remember {mutableStateOf("")}
                        val historyList = 
                            remember { mutableStateOf(emptyList<NotificationEntity>()) }

                        Text(
                            text = "Some text"
                        )
                        Button(
                            onClick = {
                                createNotification(this@MainActivity)
                            }
                        ) {
                            Text(
                                text = "Create notification"
                            )
                        }
                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    withContext(Dispatchers.Default) {
                                        val history = readHistory(applicationContext)
                                        historyList.value = history
                                        
                                        val historyText = history
                                            .joinToString(separator = "\n")
                                        Log.d(TAG, historyText)
                                        listText.value = historyText
                                    }
                                }
                            }) {
                            Text(text = "Read history")                            
                        }
                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    withContext(Dispatchers.Default) {
                                        clearHistory(applicationContext)
                                        listText.value = ""
                                    }
                                }
                            }) {
                            Text(text = "Clear history")
                        }
//                        val textScroll = rememberScrollState(0)
//                        Text(
//                            text = listText.value,
//                            modifier = Modifier.verticalScroll(textScroll)
//                        )
                        val historyScroll = rememberScrollState(0)
                        History(
                            notifications = historyList.value,
                            modifier = Modifier.verticalScroll(historyScroll)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun createNotification(context: Context) {
    Log.d("myApp", "createNotification button pressed")
    val notificationChannel = NotificationChannel(
        "channel_id_1",
        "channel 1",
        NotificationManager.IMPORTANCE_DEFAULT
    )
    val notificationBuilder = NotificationCompat.Builder(context, "channel_id_1")
        .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
        .setContentTitle("notification title")
        .setContentText("notification text")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.createNotificationChannel(notificationChannel)
    notificationManager.notify(101, notificationBuilder.build())
}

fun readHistory(context: Context): List<NotificationEntity> {
    val appDatabase = AppDatabase.getGuiInstance(context)
    return appDatabase.notificationDao().findAll()
}

fun clearHistory(context: Context) {
    val appDatabase = AppDatabase.getGuiInstance(context)
    appDatabase.notificationDao().deleteAll()
}

@Composable
fun History(notifications: List<NotificationEntity>, modifier: Modifier = Modifier) {
    Column (modifier = modifier) {
        notifications.forEach {
            NotificationFromEntity(it)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotificationCatcherTheme {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) {
            Column {
                Text(
                    text = "Some text"
                )
                Button(onClick = {

                },
                    content = {
                        Text(text = "Create notification")
                    }
                )
            }
        }
    }
}