package com.gym.feature.home.presentation.video;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b*\b\u0086\b\u0018\u00002\u00020\u0001B\u008f\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\r\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u0015J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\rH\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0006H\u00c6\u0003J\u000f\u00102\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J\u000f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\t\u00105\u001a\u00020\u0006H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\u0093\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\r2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\rH\u00c6\u0001J\u0013\u00109\u001a\u00020\u00062\b\u0010:\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010;\u001a\u00020\u0003H\u00d6\u0001J\t\u0010<\u001a\u00020\rH\u00d6\u0001R\u0011\u0010\u0016\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001eR\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0011\u0010\u0011\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001eR\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0018R\u0011\u0010#\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b$\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001aR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010(\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b)\u0010\u001aR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+\u00a8\u0006="}, d2 = {"Lcom/gym/feature/home/presentation/video/BulkImportUiState;", "", "batchSize", "", "offset", "isFetchingList", "", "exercises", "", "Lcom/gym/feature/home/data/importer/BulkImporterService$ExercisePreview;", "selectedIds", "", "fetchError", "", "isImporting", "importProgress", "importTotal", "importProgressMsg", "result", "Lcom/gym/feature/home/data/importer/ImportResult;", "importError", "(IIZLjava/util/List;Ljava/util/Set;Ljava/lang/String;ZIILjava/lang/String;Lcom/gym/feature/home/data/importer/ImportResult;Ljava/lang/String;)V", "allSelected", "getAllSelected", "()Z", "getBatchSize", "()I", "getExercises", "()Ljava/util/List;", "getFetchError", "()Ljava/lang/String;", "getImportError", "getImportProgress", "getImportProgressMsg", "getImportTotal", "noneSelected", "getNoneSelected", "getOffset", "getResult", "()Lcom/gym/feature/home/data/importer/ImportResult;", "selectedCount", "getSelectedCount", "getSelectedIds", "()Ljava/util/Set;", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "feature_home_debug"})
public final class BulkImportUiState {
    private final int batchSize = 0;
    private final int offset = 0;
    private final boolean isFetchingList = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview> exercises = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.Integer> selectedIds = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String fetchError = null;
    private final boolean isImporting = false;
    private final int importProgress = 0;
    private final int importTotal = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String importProgressMsg = null;
    @org.jetbrains.annotations.Nullable()
    private final com.gym.feature.home.data.importer.ImportResult result = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String importError = null;
    
    public BulkImportUiState(int batchSize, int offset, boolean isFetchingList, @org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview> exercises, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Integer> selectedIds, @org.jetbrains.annotations.Nullable()
    java.lang.String fetchError, boolean isImporting, int importProgress, int importTotal, @org.jetbrains.annotations.NotNull()
    java.lang.String importProgressMsg, @org.jetbrains.annotations.Nullable()
    com.gym.feature.home.data.importer.ImportResult result, @org.jetbrains.annotations.Nullable()
    java.lang.String importError) {
        super();
    }
    
    public final int getBatchSize() {
        return 0;
    }
    
    public final int getOffset() {
        return 0;
    }
    
    public final boolean isFetchingList() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview> getExercises() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.Integer> getSelectedIds() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFetchError() {
        return null;
    }
    
    public final boolean isImporting() {
        return false;
    }
    
    public final int getImportProgress() {
        return 0;
    }
    
    public final int getImportTotal() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getImportProgressMsg() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.gym.feature.home.data.importer.ImportResult getResult() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getImportError() {
        return null;
    }
    
    public final boolean getAllSelected() {
        return false;
    }
    
    public final boolean getNoneSelected() {
        return false;
    }
    
    public final int getSelectedCount() {
        return 0;
    }
    
    public BulkImportUiState() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.gym.feature.home.data.importer.ImportResult component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.Integer> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.gym.feature.home.presentation.video.BulkImportUiState copy(int batchSize, int offset, boolean isFetchingList, @org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview> exercises, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Integer> selectedIds, @org.jetbrains.annotations.Nullable()
    java.lang.String fetchError, boolean isImporting, int importProgress, int importTotal, @org.jetbrains.annotations.NotNull()
    java.lang.String importProgressMsg, @org.jetbrains.annotations.Nullable()
    com.gym.feature.home.data.importer.ImportResult result, @org.jetbrains.annotations.Nullable()
    java.lang.String importError) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}