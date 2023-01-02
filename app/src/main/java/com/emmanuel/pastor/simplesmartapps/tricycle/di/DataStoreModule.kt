package com.emmanuel.pastor.simplesmartapps.tricycle.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.emmanuel.pastor.simplesmartapps.tricycle.Dispatcher
import com.emmanuel.pastor.simplesmartapps.tricycle.DonkeDispatcher
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsAccessor
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsAccessorImpl
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleProtoBufSerializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideTricycleDataStore(
        @ApplicationContext appContext: Context,
        @Dispatcher(DonkeDispatcher.IO) ioDispatcher: CoroutineDispatcher,
    ): DataStore<TricycleDsEntity> {
        return DataStoreFactory.create(
            produceFile = {
                appContext.dataStoreFile("tricycle.pb")
            },
            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
            serializer = TricycleProtoBufSerializer,
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreAccessorModule {

    @Binds
    abstract fun bindDsAccessor(dsAccessor: TricycleDsAccessorImpl): TricycleDsAccessor
}