package com.emmanuel.pastor.simplesmartapps.tricycle.di

import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsAccessor
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsAccessorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreAccessorModule {

    @Binds
    abstract fun bindDsAccessor(dsAccessor: TricycleDsAccessorImpl): TricycleDsAccessor
}