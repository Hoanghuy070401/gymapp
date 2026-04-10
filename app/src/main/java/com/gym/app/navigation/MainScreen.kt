package com.gym.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gym.core.designsystem.theme.AppColors
import com.gym.feature.home.presentation.HomeScreen
import com.gym.feature.home.presentation.HomeViewModel
import com.gym.feature.home.presentation.admin.AdminExerciseImportScreen
import com.gym.feature.home.presentation.exerciselibrary.ExerciseDetailScreen
import com.gym.feature.home.presentation.exerciselibrary.ExerciseLibraryScreen
import com.gym.feature.home.presentation.exerciselibrary.ExerciseLibraryViewModel
import com.gym.feature.home.presentation.video.AddVideoScreen
import com.gym.feature.home.presentation.video.BulkImportScreen
import com.gym.feature.home.presentation.video.VideoDetailScreen
import com.gym.feature.workout.presentation.WorkoutScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Main app shell — manages bottom navigation + inner NavHost.
 * FAB on Home tab navigates to AddVideoScreen (dedicated screen, Approach A).
 */
@Composable
fun MainScreen() {
    val innerNav = rememberNavController()
    val backStack by innerNav.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route ?: Screen.Home.route
    val snackbarHostState = remember { SnackbarHostState() }

    val isTopLevel = currentRoute in setOf(
        Screen.Home.route,
        Screen.Workout.route,
        Screen.ExerciseLibrary.route,
        "support"
    )
    val isHomeTab = currentRoute == Screen.Home.route
    val isLibraryTab = currentRoute == Screen.ExerciseLibrary.route

    Scaffold(
        containerColor = AppColors.Surface,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (isTopLevel) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigateToHome = {
                        innerNav.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToFavorites = {
                        innerNav.navigate(Screen.ExerciseLibrary.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToWorkout = {
                        innerNav.navigate(Screen.Workout.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToSupport = { /* TODO: Support */ }
                )
            }
        },
        floatingActionButton = {
            when {
                // Home tab: Add Video
                isHomeTab -> FloatingActionButton(
                    onClick = { innerNav.navigate(Screen.AddVideo.route) },
                    shape = CircleShape,
                    containerColor = AppColors.TonalLavender,
                    contentColor = AppColors.Surface
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Thêm video")
                }
                // Library tab: Admin Import
                isLibraryTab -> FloatingActionButton(
                    onClick = { innerNav.navigate(Screen.AdminExerciseImport.route) },
                    shape = CircleShape,
                    containerColor = AppColors.ElectricLime,
                    contentColor = AppColors.Surface
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Admin Import")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            NavHost(
                navController = innerNav,
                startDestination = Screen.Home.route
            ) {
                // ── Home ─────────────────────────────────────────────────────
                composable(Screen.Home.route) {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(
                        viewModel = homeViewModel,
                        onNavigateToSearch = { /* TODO */ },
                        onNavigateToNotifications = { /* TODO */ },
                        onNavigateToProfile = { /* TODO */ },
                        onNavigateToWorkout = { innerNav.navigate(Screen.Workout.route) },
                        onNavigateToProgress = { /* TODO */ },
                        onNavigateToNutrition = { /* TODO */ },
                        onNavigateToCommunity = { /* TODO */ },
                        onNavigateToVideo = { video ->
                            innerNav.navigate(Screen.VideoDetail.route(video.id))
                        }
                    )
                }

                // ── Workout ──────────────────────────────────────────────────
                composable(Screen.Workout.route) {
                    WorkoutScreen(viewModel = hiltViewModel())
                }

                // ── Add Video (admin seed screen) ─────────────────────────────
                composable(Screen.AddVideo.route) {
                    AddVideoScreen(
                        onBack = { innerNav.popBackStack() },
                        onNavigateToBulkImport = {
                            innerNav.navigate(Screen.BulkImport.route)
                        }
                    )
                }

                // ── Bulk Import (admin tool) ──────────────────────────────────
                composable(Screen.BulkImport.route) {
                    BulkImportScreen(
                        onBack = { innerNav.popBackStack() }
                    )
                }

                // ── Exercise Library ──────────────────────────────────────────
                composable(Screen.ExerciseLibrary.route) {
                    val vm: ExerciseLibraryViewModel = hiltViewModel()
                    ExerciseLibraryScreen(
                        viewModel = vm,
                        onNavigateToDetail = { exercise ->
                            innerNav.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("selected_exercise", exercise)
                            innerNav.navigate(Screen.ExerciseDetail.route(exercise.id))
                        },
                        onBack = { innerNav.popBackStack() }
                    )
                }

                // ── Exercise Detail ───────────────────────────────────────────
                composable(
                    route = Screen.ExerciseDetail.route,
                    arguments = listOf(
                        navArgument(Screen.ExerciseDetail.ARG_EXERCISE_ID) { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val libraryEntry = remember(backStackEntry) {
                        innerNav.getBackStackEntry(Screen.ExerciseLibrary.route)
                    }
                    val exercise = libraryEntry.savedStateHandle
                        .get<com.gym.domain.model.ExerciseInfo>("selected_exercise")
                        ?: return@composable
                    ExerciseDetailScreen(
                        exercise = exercise,
                        onBack = { innerNav.popBackStack() }
                    )
                }

                // ── Admin Exercise Import ─────────────────────────────────────
                composable(Screen.AdminExerciseImport.route) {
                    AdminExerciseImportScreen(
                        onBack = { innerNav.popBackStack() }
                    )
                }

                // ── Video Detail ─────────────────────────────────────────────
                composable(
                    route = Screen.VideoDetail.route,
                    arguments = listOf(
                        navArgument(Screen.VideoDetail.ARG_VIDEO_ID) { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val videoId = backStackEntry.arguments
                        ?.getString(Screen.VideoDetail.ARG_VIDEO_ID)
                        ?: return@composable

                    val homeEntry = remember(backStackEntry) {
                        innerNav.getBackStackEntry(Screen.Home.route)
                    }
                    val homeViewModel: HomeViewModel = hiltViewModel(homeEntry)
                    val video = homeViewModel.state.value.workoutVideos.find { it.id == videoId }
                        ?: return@composable

                    VideoDetailScreen(
                        video = video,
                        onBack = { innerNav.popBackStack() }
                    )
                }
            }
        }
    }
}
