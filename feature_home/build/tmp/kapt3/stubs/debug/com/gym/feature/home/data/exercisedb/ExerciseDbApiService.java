package com.gym.feature.home.data.exercisedb;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\t\bf\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016J(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00042\b\b\u0003\u0010\u0006\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J,\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\u0005\u001a\u00020\u00042\b\b\u0003\u0010\u0006\u001a\u00020\u00042\b\b\u0001\u0010\n\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ<\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00042\b\b\u0003\u0010\u0006\u001a\u00020\u00042\b\b\u0003\u0010\r\u001a\u00020\u000e2\b\b\u0003\u0010\u000f\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0010JF\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00042\b\b\u0003\u0010\u0006\u001a\u00020\u00042\b\b\u0001\u0010\u0012\u001a\u00020\u00042\b\b\u0003\u0010\r\u001a\u00020\u000e2\b\b\u0003\u0010\u000f\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0013JF\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00042\b\b\u0003\u0010\u0006\u001a\u00020\u00042\b\b\u0001\u0010\u0015\u001a\u00020\u00042\b\b\u0003\u0010\r\u001a\u00020\u000e2\b\b\u0003\u0010\u000f\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0017"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseDbApiService;", "", "getBodyPartList", "", "", "apiKey", "host", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExerciseById", "Lcom/gym/feature/home/data/exercisedb/ExerciseDbItem;", "id", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercises", "limit", "", "offset", "(Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercisesByBodyPart", "bodyPart", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByName", "name", "Companion", "feature_home_debug"})
public abstract interface ExerciseDbApiService {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BASE_URL = "https://exercisedb.p.rapidapi.com/";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RAPID_HOST = "exercisedb.p.rapidapi.com";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.data.exercisedb.ExerciseDbApiService.Companion Companion = null;
    
    /**
     * List all exercises — paginated
     */
    @retrofit2.http.GET(value = "exercises")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getExercises(@retrofit2.http.Header(value = "X-RapidAPI-Key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Header(value = "X-RapidAPI-Host")
    @org.jetbrains.annotations.NotNull()
    java.lang.String host, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.gym.feature.home.data.exercisedb.ExerciseDbItem>> $completion);
    
    /**
     * List exercises by body part
     */
    @retrofit2.http.GET(value = "exercises/bodyPart/{bodyPart}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getExercisesByBodyPart(@retrofit2.http.Header(value = "X-RapidAPI-Key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Header(value = "X-RapidAPI-Host")
    @org.jetbrains.annotations.NotNull()
    java.lang.String host, @retrofit2.http.Path(value = "bodyPart")
    @org.jetbrains.annotations.NotNull()
    java.lang.String bodyPart, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.gym.feature.home.data.exercisedb.ExerciseDbItem>> $completion);
    
    /**
     * Search exercises by name
     */
    @retrofit2.http.GET(value = "exercises/name/{name}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchByName(@retrofit2.http.Header(value = "X-RapidAPI-Key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Header(value = "X-RapidAPI-Host")
    @org.jetbrains.annotations.NotNull()
    java.lang.String host, @retrofit2.http.Path(value = "name")
    @org.jetbrains.annotations.NotNull()
    java.lang.String name, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.gym.feature.home.data.exercisedb.ExerciseDbItem>> $completion);
    
    /**
     * Get exercise detail by ID
     */
    @retrofit2.http.GET(value = "exercises/exercise/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getExerciseById(@retrofit2.http.Header(value = "X-RapidAPI-Key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Header(value = "X-RapidAPI-Host")
    @org.jetbrains.annotations.NotNull()
    java.lang.String host, @retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.home.data.exercisedb.ExerciseDbItem> $completion);
    
    /**
     * Get all available body part categories
     */
    @retrofit2.http.GET(value = "exercises/bodyPartList")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getBodyPartList(@retrofit2.http.Header(value = "X-RapidAPI-Key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Header(value = "X-RapidAPI-Host")
    @org.jetbrains.annotations.NotNull()
    java.lang.String host, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseDbApiService$Companion;", "", "()V", "BASE_URL", "", "RAPID_HOST", "feature_home_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String BASE_URL = "https://exercisedb.p.rapidapi.com/";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String RAPID_HOST = "exercisedb.p.rapidapi.com";
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}