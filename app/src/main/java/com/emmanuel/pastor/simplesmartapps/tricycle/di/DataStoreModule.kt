package com.emmanuel.pastor.simplesmartapps.tricycle.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleProtoBufSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideTricycleDataStore(@ApplicationContext appContext: Context): DataStore<TricycleDsEntity> {
        return DataStoreFactory.create(
            produceFile = {
                appContext.dataStoreFile("tricycle.pb")
            },
            serializer = TricycleProtoBufSerializer,
        )
    }
}