package com.gym.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WorkoutEntity::class], version = 1, exportSchema = false)
abstract class GymDatabase : RoomDatabase() {
    abstract val workoutDao: WorkoutDao
}
