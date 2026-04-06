package com.gym.feature.auth.presentation.setup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.component.GymButton
import com.gym.core.designsystem.component.GymButtonVariant
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.component.StepProgressBar
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography

/** Returns true when the user has satisfied all required inputs for [step]. */
private fun canProceed(step: Int, state: SetupState): Boolean = when (step) {
    0 -> true                                           // Intro — no validation
    1 -> state.gender.isNotBlank()                      // Must pick gender
    2 -> true                                           // Age wheel — always has value
    3 -> true                                           // Weight wheel — always has value
    4 -> true                                           // Height wheel — always has value
    5 -> state.goal.isNotBlank()                        // Must pick goal
    6 -> state.activityLevel.isNotBlank()               // Must pick activity level
    7 -> state.nickname.isNotBlank()                    // Nickname required
            && state.dateOfBirth.isNotBlank()           // Date of birth required
            && state.mobileNumber.isNotBlank()          // Mobile number required
    else -> true
}

private val stepTitles = listOf(
    "Set Up",
    "What's your gender",
    "How old are you",
    "What is your weight",
    "What is your Height",
    "What is your goal",
    "Physical activity level",
    "Fill your profile"
)

private const val TOTAL_STEPS = 8
private const val DATA_STEPS = 7 // steps 1–7, excludes intro step 0

@Composable
fun SetupScreen(
    onFinished: () -> Unit,
    setupViewModel: SetupViewModel
) {
    val state by setupViewModel.state.collectAsState()
    var currentStep by remember { mutableIntStateOf(0) }

    GymScaffold(scrollable = false) {
        // Top Bar: ◀ Back + Step Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal)
                .padding(bottom = AppSpacing.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentStep > 0) {
                Text(
                    text = "◀",
                    style = AppTypography.bodyLarge,
                    color = AppColors.ElectricLime,
                    modifier = Modifier.clickable {
                        setupViewModel.clearError()
                        currentStep -= 1
                    }
                )
            } else {
                Text(text = "◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stepTitles.getOrElse(currentStep) { "" },
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.ElectricLime
            )
            Spacer(modifier = Modifier.weight(1f))
            // Invisible spacer for centering
            Text(text = "◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
        }

        // Step progress bar (hidden on intro step 0)
        if (currentStep > 0) {
            StepProgressBar(
                totalSteps = DATA_STEPS,
                currentStep = currentStep - 1, // 0-indexed within data steps
                modifier = Modifier.padding(bottom = AppSpacing.Small)
            )
        }

        // Step Content
        Box(modifier = Modifier.weight(1f)) {
            when (currentStep) {
                0 -> SetupIntroStep()
                1 -> GenderStep(state.gender) { setupViewModel.updateGender(it) }
                2 -> AgeStep(state.age) { setupViewModel.updateAge(it) }
                3 -> WeightStep(state.weightKg) { setupViewModel.updateWeight(it) }
                4 -> HeightStep(state.heightCm) { setupViewModel.updateHeight(it) }
                5 -> GoalStep(state.goal) { setupViewModel.updateGoal(it) }
                6 -> ActivityLevelStep(state.activityLevel) { setupViewModel.updateActivityLevel(it) }
                7 -> FillProfileStep(
                    state = state,
                    onUpdate = { nick, mobile, dob ->
                        setupViewModel.updateProfile(nick, mobile, dob)
                    },
                    onPickImage = { uri ->
                        setupViewModel.saveProfileImage(uri)
                    }
                )
            }
        }

        // Error message
        if (state.error != null) {
            Text(
                text = state.error!!,
                style = AppTypography.bodySmall,
                color = AppColors.Error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.ScreenHorizontal)
                    .padding(bottom = AppSpacing.Small)
            )
        }

        // Bottom Button / Loading indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = AppColors.ElectricLime)
            } else {
                val buttonText = when (currentStep) {
                    0 -> "Next"
                    TOTAL_STEPS - 1 -> "Start"
                    else -> "Continue"
                }
                val enabled = canProceed(currentStep, state)
                GymButton(
                    text = buttonText,
                    onClick = {
                        if (!enabled) return@GymButton
                        if (currentStep == TOTAL_STEPS - 1) {
                            setupViewModel.syncToFirebaseAndComplete(onFinished)
                        } else {
                            setupViewModel.clearError()
                            currentStep += 1
                        }
                    },
                    variant = if (enabled) GymButtonVariant.GHOST else GymButtonVariant.GHOST,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled
                )
            }
        }
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
    }
}
