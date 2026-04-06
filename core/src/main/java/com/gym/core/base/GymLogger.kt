package com.gym.core.base

import timber.log.Timber

/**
 * Centralized logger — mọi log đều dùng tag "ots" để dễ filter trên Logcat.
 * Class name được nhúng vào message dưới dạng [tag] prefix để vẫn biết log đến từ đâu.
 *
 * Logcat filter: tag:ots
 */
object GymLogger {

    private const val GLOBAL_TAG = "ots"

    /** Debug — verbose flow tracing, omitted in release builds. */
    fun d(tag: String, msg: String) =
        Timber.tag(GLOBAL_TAG).d("[$tag] $msg")

    /** Info — significant milestones (success, navigation). */
    fun i(tag: String, msg: String) =
        Timber.tag(GLOBAL_TAG).i("[$tag] $msg")

    /** Warning — recoverable issues worth attention. */
    fun w(tag: String, msg: String) =
        Timber.tag(GLOBAL_TAG).w("[$tag] $msg")

    /**
     * Error — với [throwable] để Crashlytics capture đủ stack trace.
     * Luôn dùng variant này khi có exception — không bao giờ nuốt throwable.
     */
    fun e(tag: String, throwable: Throwable, msg: String) =
        Timber.tag(GLOBAL_TAG).e(throwable, "[$tag] $msg")

    /** Error — message only (không có throwable), dùng cho validation errors. */
    fun e(tag: String, msg: String) =
        Timber.tag(GLOBAL_TAG).e("[$tag] $msg")
}
