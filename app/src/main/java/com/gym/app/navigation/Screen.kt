package com.gym.app.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Login : Screen("login")
    data object SignUp : Screen("signup")
    data object ForgotPassword : Screen("forgot_password")
    data object SetPassword : Screen("set_password")
    data object SetFingerprint : Screen("set_fingerprint")
    data object EmailVerification : Screen("email_verification")
    data object Setup : Screen("setup")
    data object Home : Screen("home")
    data object Workout : Screen("workout")
    data object AddVideo : Screen("add_video")
    data object BulkImport : Screen("bulk_import")
    data object ExerciseLibrary : Screen("exercise_library")
    data object AdminExerciseImport : Screen("admin_exercise_import")
    data object ExerciseDetail : Screen("exercise_detail/{exerciseId}") {
        const val ARG_EXERCISE_ID = "exerciseId"
        fun route(exerciseId: String) = "exercise_detail/$exerciseId"
    }
    data object VideoDetail : Screen("video_detail/{videoId}") {
        const val ARG_VIDEO_ID = "videoId"
        fun route(videoId: String) = "video_detail/$videoId"
    }
}

