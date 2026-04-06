package com.gym.core.base;

/**
 * Centralized logger — mọi log đều dùng tag "ots" để dễ filter trên Logcat.
 * Class name được nhúng vào message dưới dạng [tag] prefix để vẫn biết log đến từ đâu.
 *
 * Logcat filter: tag:ots
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0016\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u001e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0004J\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0016\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/gym/core/base/GymLogger;", "", "()V", "GLOBAL_TAG", "", "d", "", "tag", "msg", "e", "throwable", "", "i", "w", "core_debug"})
public final class GymLogger {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String GLOBAL_TAG = "ots";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.core.base.GymLogger INSTANCE = null;
    
    private GymLogger() {
        super();
    }
    
    /**
     * Debug — verbose flow tracing, omitted in release builds.
     */
    public final void d(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    java.lang.String msg) {
    }
    
    /**
     * Info — significant milestones (success, navigation).
     */
    public final void i(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    java.lang.String msg) {
    }
    
    /**
     * Warning — recoverable issues worth attention.
     */
    public final void w(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    java.lang.String msg) {
    }
    
    /**
     * Error — với [throwable] để Crashlytics capture đủ stack trace.
     * Luôn dùng variant này khi có exception — không bao giờ nuốt throwable.
     */
    public final void e(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    java.lang.Throwable throwable, @org.jetbrains.annotations.NotNull()
    java.lang.String msg) {
    }
    
    /**
     * Error — message only (không có throwable), dùng cho validation errors.
     */
    public final void e(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    java.lang.String msg) {
    }
}