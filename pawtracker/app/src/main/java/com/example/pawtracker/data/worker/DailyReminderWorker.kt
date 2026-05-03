package com.example.pawtracker.data.worker

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pawtracker.R
import com.example.pawtracker.utils.NotificationHelper

class DailyReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        //  Check notification permission (Android 13+)
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.success() // skip silently
        }

        //  Build notification
        val notification =
            NotificationCompat.Builder(applicationContext, NotificationHelper.CHANNEL_ID)
                .setContentTitle("Time for a walk!")
                .setContentText("Buddy is waiting for his daily walk.")
                .setSmallIcon(R.drawable.notification_icon)
                .setAutoCancel(true)
                .build()

        // Show notification
        NotificationManagerCompat.from(applicationContext).notify(1001, notification)

        //  MUST return a Result
        return Result.success()
    }
}
