package com.gym.feature.auth.presentation.setup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.theme.AppColors

// ─────────────────────────────────────────────────────────────────────────────
// Setup step previews — each step composable is self-contained and previewable
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Setup — Step 0: Intro", widthDp = 390, heightDp = 844)
@Composable
fun SetupIntroStepPreview() {
    Box(modifier = Modifier.fillMaxSize().background(AppColors.Surface)) {
        SetupIntroStep()
    }
}

@Preview(showBackground = true, name = "Setup — Step 1: Gender (None)", widthDp = 390, heightDp = 844)
@Composable
fun GenderStepEmptyPreview() {
    GymScaffold(scrollable = false) {
        GenderStep(selectedGender = "") {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 1: Gender (Male)", widthDp = 390, heightDp = 844)
@Composable
fun GenderStepMalePreview() {
    GymScaffold(scrollable = false) {
        GenderStep(selectedGender = "Male") {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 1: Gender (Female)", widthDp = 390, heightDp = 844)
@Composable
fun GenderStepFemalePreview() {
    GymScaffold(scrollable = false) {
        GenderStep(selectedGender = "Female") {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 2: Age", widthDp = 390, heightDp = 844)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgeStepPreview() {
    GymScaffold(scrollable = false) {
        AgeStep(currentAge = 25) {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 3: Weight", widthDp = 390, heightDp = 844)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeightStepPreview() {
    GymScaffold(scrollable = false) {
        WeightStep(currentWeight = 70f) {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 4: Height", widthDp = 390, heightDp = 844)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeightStepPreview() {
    GymScaffold(scrollable = false) {
        HeightStep(currentHeight = 172) {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 5: Goal (Empty)", widthDp = 390, heightDp = 844)
@Composable
fun GoalStepEmptyPreview() {
    GymScaffold(scrollable = false) {
        GoalStep(selectedGoal = "") {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 5: Goal (Selected)", widthDp = 390, heightDp = 844)
@Composable
fun GoalStepSelectedPreview() {
    GymScaffold(scrollable = false) {
        GoalStep(selectedGoal = "Lose Weight") {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 6: Activity Level", widthDp = 390, heightDp = 844)
@Composable
fun ActivityLevelStepPreview() {
    GymScaffold(scrollable = false) {
        ActivityLevelStep(selectedLevel = "Moderately Active") {}
    }
}

@Preview(showBackground = true, name = "Setup — Step 7: Fill Profile", widthDp = 390, heightDp = 844)
@Composable
fun FillProfileStepPreview() {
    val previewState = SetupState(
        nickname = "JohnFit",
        mobileNumber = "+84 912 345 678",
        dateOfBirth = "01/01/1995"
    )
    GymScaffold(scrollable = false) {
        FillProfileStep(
            state = previewState,
            onUpdate = { _, _, _ -> },
            onPickImage = {}
        )
    }
}

@Preview(showBackground = true, name = "Setup — Step 7: Fill Profile (Empty)", widthDp = 390, heightDp = 844)
@Composable
fun FillProfileStepEmptyPreview() {
    GymScaffold(scrollable = false) {
        FillProfileStep(
            state = SetupState(),
            onUpdate = { _, _, _ -> },
            onPickImage = {}
        )
    }
}
