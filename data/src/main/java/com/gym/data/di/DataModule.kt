package com.gym.data.di

import android.content.Context
import androidx.room.Room
import com.gym.data.local.GymDatabase
import com.gym.data.local.WorkoutDao
import com.gym.data.repository.WorkoutRepositoryImpl
import com.gym.domain.repository.WorkoutRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGymDatabase(
        @ApplicationContext context: Context
    ): GymDatabase {
        return Room.databaseBuilder(
            context,
            GymDatabase::class.java,
            "gym_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(database: GymDatabase): WorkoutDao {
        return database.workoutDao
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(
        workoutRepositoryImpl: WorkoutRepositoryImpl
    ): WorkoutRepository
}
