package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store

import kotlinx.coroutines.flow.Flow

interface TricycleDsAccessor {
    fun getTricycleData(): Flow<TricycleDsEntity>

    suspend fun updateTricycleData(tricycleDsEntity: TricycleDsEntity)
}