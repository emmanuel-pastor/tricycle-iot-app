package com.emmanuel.pastor.simplesmartapps.tricycle.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.TricycleBleAccessor
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.TricycleBleAccessorImpl
import com.emmanuel.pastor.simplesmartapps.tricycle.platform.BleClient
import com.emmanuel.pastor.simplesmartapps.tricycle.platform.BleClientImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BleModule {
    @Singleton
    @Provides
    fun provideBluetoothAdapter(@ApplicationContext appContext: Context): BluetoothAdapter {
        val bluetoothManager: BluetoothManager = appContext.getSystemService(BluetoothManager::class.java)
        return bluetoothManager.adapter
    }

    @Singleton
    @Provides
    fun provideBleClient(@ApplicationContext appContext: Context, bleAdapter: BluetoothAdapter): BleClient {
        return BleClientImpl(appContext, bleAdapter)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BleAccessorModule {
    @Binds
    abstract fun bindBleAccessor(bleAccessor: TricycleBleAccessorImpl): TricycleBleAccessor
}