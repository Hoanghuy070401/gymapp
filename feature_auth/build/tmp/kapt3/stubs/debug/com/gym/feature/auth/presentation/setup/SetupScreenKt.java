package com.gym.feature.auth.presentation.setup;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"DATA_STEPS", "", "TOTAL_STEPS", "stepTitles", "", "", "SetupScreen", "", "onFinished", "Lkotlin/Function0;", "setupViewModel", "Lcom/gym/feature/auth/presentation/setup/SetupViewModel;", "canProceed", "", "step", "state", "Lcom/gym/feature/auth/presentation/setup/SetupState;", "feature_auth_debug"})
public final class SetupScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> stepTitles = null;
    private static final int TOTAL_STEPS = 8;
    private static final int DATA_STEPS = 7;
    
    /**
     * Returns true when the user has satisfied all required inputs for [step].
     */
    private static final boolean canProceed(int step, com.gym.feature.auth.presentation.setup.SetupState state) {
        return false;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SetupScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFinished, @org.jetbrains.annotations.NotNull()
    com.gym.feature.auth.presentation.setup.SetupViewModel setupViewModel) {
    }
}