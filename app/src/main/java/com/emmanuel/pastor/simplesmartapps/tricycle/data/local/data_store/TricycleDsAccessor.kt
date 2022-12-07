package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store

import com.emmanuel.pastor.simplesmartapps.tricycle.TricycleProtoEntity
import kotlinx.coroutines.flow.Flow

interface TricycleDsAccessor {
    fun getTricycleData(): Flow<TricycleProtoEntity>

    suspend fun storeTricycleData(tricycleProtoEntity: TricycleProtoEntity)
}