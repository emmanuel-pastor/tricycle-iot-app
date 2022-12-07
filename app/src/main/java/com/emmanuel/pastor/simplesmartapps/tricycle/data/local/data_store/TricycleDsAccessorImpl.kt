package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store

import androidx.datastore.core.DataStore
import com.emmanuel.pastor.simplesmartapps.tricycle.TricycleProtoEntity
import kotlinx.coroutines.flow.Flow

class TricycleDsAccessorImpl(private val dataStore: DataStore<TricycleProtoEntity>) : TricycleDsAccessor {
    override fun getTricycleData(): Flow<TricycleProtoEntity> {
        return dataStore.data
    }

    override suspend fun storeTricycleData(tricycleProtoEntity: TricycleProtoEntity) {
        dataStore.updateData { tricycleProtoEntity }
    }
}