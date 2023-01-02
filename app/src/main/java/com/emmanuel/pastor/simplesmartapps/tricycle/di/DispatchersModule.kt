package com.emmanuel.pastor.simplesmartapps.tricycle.di

import com.emmanuel.pastor.simplesmartapps.tricycle.Dispatcher
import com.emmanuel.pastor.simplesmartapps.tricycle.DonkeDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(DonkeDispatcher.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}