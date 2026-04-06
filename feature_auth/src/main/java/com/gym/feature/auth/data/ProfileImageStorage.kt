package com.gym.feature.auth.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

/**
 * Manages profile image persistence.
 * Copies picked image to app-internal storage so it survives gallery changes.
 * Stores the internal file path in SharedPreferences for quick retrieval.
 */
class ProfileImageStorage(private val context: Context) {

    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Copy the image from a content URI to internal storage and persist the path.
     * Returns the saved file path, or null on failure.
     */
    fun saveProfileImage(uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            // Scale down if too large (max 512x512 for a profile pic)
            val scaled = scaleBitmap(bitmap, MAX_SIZE)

            val file = File(context.filesDir, PROFILE_IMAGE_FILENAME)
            FileOutputStream(file).use { out ->
                scaled.compress(Bitmap.CompressFormat.JPEG, QUALITY, out)
            }

            val path = file.absolutePath
            prefs.edit().putString(KEY_AVATAR_PATH, path).apply()
            path
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Get the saved profile image file path, or null if none exists.
     */
    fun getSavedImagePath(): String? {
        val path = prefs.getString(KEY_AVATAR_PATH, null) ?: return null
        val file = File(path)
        return if (file.exists()) path else null
    }

    /**
     * Load saved profile image as Bitmap, or null if not available.
     */
    fun loadProfileBitmap(): Bitmap? {
        val path = getSavedImagePath() ?: return null
        return try {
            BitmapFactory.decodeFile(path)
        } catch (e: Exception) {
            null
        }
    }

    private fun scaleBitmap(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        if (width <= maxDimension && height <= maxDimension) return bitmap

        val ratio = width.toFloat() / height.toFloat()
        val newWidth: Int
        val newHeight: Int
        if (width > height) {
            newWidth = maxDimension
            newHeight = (maxDimension / ratio).toInt()
        } else {
            newHeight = maxDimension
            newWidth = (maxDimension * ratio).toInt()
        }
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    companion object {
        private const val PREFS_NAME = "gym_profile_prefs"
        private const val KEY_AVATAR_PATH = "avatar_image_path"
        private const val PROFILE_IMAGE_FILENAME = "profile_avatar.jpg"
        private const val MAX_SIZE = 512
        private const val QUALITY = 85
    }
}
