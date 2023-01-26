package com.example.networkinteractions.worker


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.networkinteractions.R


class MyFirstWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        log("doWork")
        try {
            createNotificationChannel()
            getForegroundInfo()
        } catch (e: Exception) {
            log(e.message.toString())
            return Result.retry()
        }
        return Result.success()
    }


    override suspend fun getForegroundInfo(): ForegroundInfo {
        log("foregroundInfo")
        return ForegroundInfo(1, notification())
    }

    private fun notification(): Notification {
        log("notification")
        val intent = WorkManager.getInstance(context)
            .createCancelPendingIntent(id)

        val cancel = "cancel"

        return NotificationCompat.Builder(context, "id")
            .setContentTitle("title")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Text for WorkManager")
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()
    }


    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as
            NotificationManager

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "id",
                "name",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "WorkManager: $message")
    }
}