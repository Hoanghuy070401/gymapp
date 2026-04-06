package com.gym.feature.auth.di

import android.content.Context
import com.gym.feature.auth.data.AuthRepository
import com.gym.feature.auth.data.ProfileImageStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepository()

    @Provides
    @Singleton
    fun provideProfileImageStorage(
        @ApplicationContext context: Context
    ): ProfileImageStorage = ProfileImageStorage(context)
}
