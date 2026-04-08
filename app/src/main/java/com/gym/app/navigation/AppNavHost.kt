package com.gym.app.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gym.feature.auth.presentation.AuthViewModel
import com.gym.feature.auth.presentation.emailverification.EmailVerificationScreen
import com.gym.feature.auth.presentation.fingerprint.SetFingerprintScreen
import com.gym.feature.auth.presentation.forgotpassword.ForgotPasswordScreen
import com.gym.feature.auth.presentation.login.LoginScreen
import com.gym.feature.auth.presentation.setpassword.SetPasswordScreen
import com.gym.feature.auth.presentation.setup.SetupScreen
import com.gym.feature.auth.presentation.signup.SignUpScreen
import com.gym.feature.onboarding.presentation.OnboardingScreen

private const val PREFS_NAME = "gym_prefs"
private const val KEY_ONBOARDING_DONE = "onboarding_completed"

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    // Shared AuthViewModel across Login + SignUp
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // --- LAUNCH ---
        composable(Screen.Splash.route) {
            SplashScreen(
                authViewModel = authViewModel,
                onNavigateToOnboarding = {
                    // Show Onboarding only the first time — skip to Login on subsequent launches
                    val onboardingDone = prefs.getBoolean(KEY_ONBOARDING_DONE, false)
                    if (onboardingDone) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                },
                onLoggedIn = {
                    // Persisted session + verified + setup done → Home
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNeedsSetup = {
                    // Persisted session + verified + setup NOT done → Setup wizard
                    navController.navigate(Screen.Setup.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNeedsVerification = {
                    // Persisted session + email NOT verified
                    // Navigate: Splash → Login → EmailVerification
                    // Stack = [Login, EmailVerification] so back naturally goes to Login
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                    navController.navigate(Screen.EmailVerification.route)
                }
            )
        }

        // --- ONBOARDING ---
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinished = {
                    // Mark onboarding as seen so it's skipped on future launches
                    prefs.edit().putBoolean(KEY_ONBOARDING_DONE, true).apply()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // --- AUTH ---
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    // Verified + setup already done → go straight to Home
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onLoginNeedsSetup = {
                    // Verified + first login / setup not done → Setup wizard
                    navController.navigate(Screen.Setup.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onLoginNeedsVerification = {
                    // Email/password login but not yet verified
                    // Stack = [Login, EmailVerification] so back naturally goes to Login
                    navController.navigate(Screen.EmailVerification.route) {
                        popUpTo(Screen.Login.route) { inclusive = false }
                    }
                },
                onNavigateToSignUp = {
                    authViewModel.clearError()
                    navController.navigate(Screen.SignUp.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                viewModel = authViewModel,
                onSignUpSuccess = {
                    // Pop SignUp off the stack before navigating to EmailVerification
                    // Stack result: [Login, EmailVerification]
                    // → back from EmailVerification always returns to Login (not SignUp)
                    navController.navigate(Screen.EmailVerification.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    authViewModel.clearError()
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.EmailVerification.route) {
            EmailVerificationScreen(
                viewModel = authViewModel,
                onVerified = {
                    // Email confirmed — clear entire auth stack, go to Setup
                    navController.navigate(Screen.Setup.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onBack = {
                    // Sign out before returning to Login so user doesn't get
                    // stuck in an infinite Login → EmailVerification loop
                    authViewModel.signOut()
                    authViewModel.clearError()
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onContinue = { _ ->
                    navController.navigate(Screen.SetPassword.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.SetPassword.route) {
            SetPasswordScreen(
                onResetPassword = { _, _ ->
                    navController.navigate(Screen.SetFingerprint.route) {
                        popUpTo(Screen.ForgotPassword.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.SetFingerprint.route) {
            SetFingerprintScreen(
                onSkip = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SetFingerprint.route) { inclusive = true }
                    }
                },
                onContinue = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SetFingerprint.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // --- SETUP WIZARD ---
        composable(Screen.Setup.route) {
            SetupScreen(
                setupViewModel = hiltViewModel(),
                onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Setup.route) { inclusive = true }
                    }
                }
            )
        }

        // --- MAIN APP SHELL (Home + Workout + VideoDetail + BottomNav) ---
        composable(Screen.Home.route) {
            MainScreen()
        }
    }
}
