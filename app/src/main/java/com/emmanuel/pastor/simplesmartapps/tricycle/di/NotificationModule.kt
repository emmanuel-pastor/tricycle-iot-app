package com.emmanuel.pastor.simplesmartapps.tricycle.di

import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext appContext: Context): NotificationManager {
        return appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}