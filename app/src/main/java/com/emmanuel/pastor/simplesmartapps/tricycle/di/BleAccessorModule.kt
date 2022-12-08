package com.emmanuel.pastor.simplesmartapps.tricycle.di

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.TricycleBleAccessor
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.TricycleBleAccessorFake
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BleAccessorModule {

    @OptIn(ExperimentalUnsignedTypes::class)
    @Binds
    abstract fun bindBleAccessor(bleAccessor: TricycleBleAccessorFake): TricycleBleAccessor
}