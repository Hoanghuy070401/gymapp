package com.gym.app

import android.app.Application
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.gym.app.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GymApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Verbose debug logs only in debug builds
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Always send ERROR-level logs + exceptions to Crashlytics
        Timber.plant(CrashlyticsTree())
    }
}

/**
 * Timber Tree that forwards every ERROR-priority log and its attached
 * [Throwable] to Firebase Crashlytics.
 *
 * - Non-fatal exceptions are recorded so they appear in the Crashlytics dashboard.
 * - Log messages without a throwable are added as a Crashlytics key/log line for context.
 */
private class CrashlyticsTree : Timber.Tree() {

    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun isLoggable(tag: String?, priority: Int): Boolean =
        priority >= Log.ERROR

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        crashlytics.log("${tag ?: "GymApp"}: $message")
        if (t != null) {
            crashlytics.recordException(t)
        }
    }
}
