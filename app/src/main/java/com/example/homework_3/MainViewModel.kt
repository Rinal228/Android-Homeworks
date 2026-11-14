package com.example.homework_3

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    var title by mutableStateOf("")
    var text by mutableStateOf("")
    var isOngoing by mutableStateOf(false)
    var importanceIndex by mutableStateOf(1)
    var editIdText by mutableStateOf("")
    var hasActiveNotifications by mutableStateOf(false)
        private set
    private var notificationCounter = 1
    init {
        createChannels()
        viewModelScope.launch {
            while (true) {
                checkActiveNotifications()
                delay(1000)
            }
        }
    }
    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val minChannel = NotificationChannel(
                "chan_min",
                "Min Importance",
                NotificationManager.IMPORTANCE_MIN
            )
            val defaultChannel = NotificationChannel("chan_def", "Default Importance", NotificationManager.IMPORTANCE_DEFAULT)
            val highChannel = NotificationChannel("chan_high", "High Importance", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannels(listOf(minChannel, defaultChannel, highChannel))
        }
    }
    fun sendNotification(specificId: Int? = null) {
        val channelId = when (importanceIndex) {
            0 -> "chan_min"
            2 -> "chan_high"
            else -> "chan_def"
        }
        val idToUse = specificId ?: notificationCounter++
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("EXTRA_TITLE", title)
            putExtra("EXTRA_TEXT", text)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            idToUse,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(if (title.isBlank()) "Уведомление $idToUse" else title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setOngoing(isOngoing)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(idToUse, builder.build())
        checkActiveNotifications()
    }
    fun updateNotification() {
        val id = editIdText.toIntOrNull()
        if (id != null) {
            sendNotification(id)
        } else {
            Toast.makeText(context, "Введи корректный ID", Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteAll() {
        notificationManager.cancelAll()
        checkActiveNotifications()
    }
    private fun checkActiveNotifications() {
        hasActiveNotifications = notificationManager.activeNotifications.isNotEmpty()
    }
}