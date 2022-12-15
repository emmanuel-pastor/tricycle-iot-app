package com.emmanuel.pastor.simplesmartapps.tricycle.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {
    @Singleton
    @Provides
    fun provideBluetoothAdapter(@ApplicationContext appContext: Context): BluetoothAdapter {
        val bluetoothManager: BluetoothManager = appContext.getSystemService(BluetoothManager::class.java)
        return bluetoothManager.adapter
    }
}