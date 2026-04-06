package com.gym.feature.auth.data;

/**
 * Manages profile image persistence.
 * Copies picked image to app-internal storage so it survives gallery changes.
 * Stores the internal file path in SharedPreferences for quick retrieval.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u0004\u0018\u00010\rJ\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0011\u001a\u00020\u0012J\u0018\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0018"}, d2 = {"Lcom/gym/feature/auth/data/ProfileImageStorage;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "getPrefs", "()Landroid/content/SharedPreferences;", "prefs$delegate", "Lkotlin/Lazy;", "getSavedImagePath", "", "loadProfileBitmap", "Landroid/graphics/Bitmap;", "saveProfileImage", "uri", "Landroid/net/Uri;", "scaleBitmap", "bitmap", "maxDimension", "", "Companion", "feature_auth_debug"})
public final class ProfileImageStorage {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy prefs$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "gym_profile_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AVATAR_PATH = "avatar_image_path";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PROFILE_IMAGE_FILENAME = "profile_avatar.jpg";
    private static final int MAX_SIZE = 512;
    private static final int QUALITY = 85;
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.auth.data.ProfileImageStorage.Companion Companion = null;
    
    public ProfileImageStorage(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.content.SharedPreferences getPrefs() {
        return null;
    }
    
    /**
     * Copy the image from a content URI to internal storage and persist the path.
     * Returns the saved file path, or null on failure.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String saveProfileImage(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * Get the saved profile image file path, or null if none exists.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSavedImagePath() {
        return null;
    }
    
    /**
     * Load saved profile image as Bitmap, or null if not available.
     */
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap loadProfileBitmap() {
        return null;
    }
    
    private final android.graphics.Bitmap scaleBitmap(android.graphics.Bitmap bitmap, int maxDimension) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/gym/feature/auth/data/ProfileImageStorage$Companion;", "", "()V", "KEY_AVATAR_PATH", "", "MAX_SIZE", "", "PREFS_NAME", "PROFILE_IMAGE_FILENAME", "QUALITY", "feature_auth_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}