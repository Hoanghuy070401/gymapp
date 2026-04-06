package com.gym.app;

/**
 * Timber Tree that forwards every ERROR-priority log and its attached
 * [Throwable] to Firebase Crashlytics.
 *
 * - Non-fatal exceptions are recorded so they appear in the Crashlytics dashboard.
 * - Log messages without a throwable are added as a Crashlytics key/log line for context.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0014J,\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/gym/app/CrashlyticsTree;", "Ltimber/log/Timber$Tree;", "()V", "crashlytics", "Lcom/google/firebase/crashlytics/FirebaseCrashlytics;", "isLoggable", "", "tag", "", "priority", "", "log", "", "message", "t", "", "app_debug"})
final class CrashlyticsTree extends timber.log.Timber.Tree {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.crashlytics.FirebaseCrashlytics crashlytics = null;
    
    public CrashlyticsTree() {
        super();
    }
    
    @java.lang.Override()
    protected boolean isLoggable(@org.jetbrains.annotations.Nullable()
    java.lang.String tag, int priority) {
        return false;
    }
    
    @java.lang.Override()
    protected void log(int priority, @org.jetbrains.annotations.Nullable()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    java.lang.Throwable t) {
    }
}