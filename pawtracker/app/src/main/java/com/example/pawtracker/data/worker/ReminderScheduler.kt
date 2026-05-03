package com.example.pawtracker.data.worker

import androidx.work.*
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import android.content.Context

object ReminderScheduler {

    fun scheduleDailyReminder(context: Context) {

        val workRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)

            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_walk_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    private fun calculateInitialDelay(): Long {
        val now = LocalTime.now()
        val target = LocalTime.of(9, 0) // 9:00 AM reminder

        return if (now.isBefore(target)) {
            Duration.between(now, target).toMillis()
        } else {
            Duration.between(now, target.plusHours(24)).toMillis()
        }
    }
}