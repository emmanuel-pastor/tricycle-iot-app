package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store

import kotlinx.coroutines.flow.Flow

interface TricycleDsAccessor {
    fun getTricycleData(): Flow<TricycleDsEntity>

    /**
     * Updates the data store with the new data
     * @param tricycleDsEntity the new data to be saved
     * Every null value in [TricycleDsEntity] will be ignored
     */
    suspend fun updateTricycleData(tricycleDsEntity: TricycleDsEntity)
}