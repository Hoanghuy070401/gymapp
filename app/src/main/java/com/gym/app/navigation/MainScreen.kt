package com.gym.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gym.core.designsystem.theme.AppColors
import com.gym.feature.home.presentation.HomeScreen
import com.gym.feature.home.presentation.HomeViewModel
import com.gym.feature.home.data.WorkoutVideo
import com.gym.feature.home.presentation.video.VideoDetailScreen
import com.gym.feature.workout.presentation.WorkoutScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.runtime.remember

/**
 * Main app shell — wraps Home + Workout + future tabs with Bottom Navigation.
 * Separate from auth navigation to keep auth flow clean.
 *
 * Navigation within main is managed by an internal [NavHostController].
 */
@Composable
fun MainScreen(
    onLogout: () -> Unit = {}
) {
    val innerNav = rememberNavController()
    val backStack by innerNav.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route ?: Screen.Home.route

    // Only show bottom nav on top-level destinations
    val showBottomNav = currentRoute in setOf(
        Screen.Home.route,
        Screen.Workout.route,
        "favorites",
        "support"
    )

    Scaffold(
        containerColor = AppColors.Surface,
        bottomBar = {
            if (showBottomNav) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigateToHome = {
                        innerNav.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToFavorites = { /* TODO: Favorites */ },
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
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            NavHost(
                navController = innerNav,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(
                        viewModel = homeViewModel,
                        onNavigateToSearch = { /* TODO */ },
                        onNavigateToNotifications = { /* TODO */ },
                        onNavigateToProfile = { /* TODO */ },
                        onNavigateToWorkout = {
                            innerNav.navigate(Screen.Workout.route)
                        },
                        onNavigateToProgress = { /* TODO */ },
                        onNavigateToNutrition = { /* TODO */ },
                        onNavigateToCommunity = { /* TODO */ },
                        onNavigateToVideo = { video ->
                            innerNav.navigate(Screen.VideoDetail.route(video.id))
                        }
                    )
                }

                composable(Screen.Workout.route) {
                    WorkoutScreen(viewModel = hiltViewModel())
                }

                // Video Detail
                composable(
                    route = Screen.VideoDetail.route,
                    arguments = listOf(
                        navArgument(Screen.VideoDetail.ARG_VIDEO_ID) { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val videoId = backStackEntry.arguments
                        ?.getString(Screen.VideoDetail.ARG_VIDEO_ID)
                        ?: return@composable

                    // Retrieve video from HomeViewModel scoped to home back-stack entry
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
