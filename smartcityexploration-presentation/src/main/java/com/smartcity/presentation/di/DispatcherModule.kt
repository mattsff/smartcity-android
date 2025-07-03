package com.smartcity.presentation.di

import com.smartcity.presentation.utils.dispatcher.CoroutineDispatcherProvider
import com.smartcity.presentation.utils.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun providesDispatcher(): DispatcherProvider = CoroutineDispatcherProvider()
}
