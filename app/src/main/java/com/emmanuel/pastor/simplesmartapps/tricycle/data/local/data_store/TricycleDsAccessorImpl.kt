package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow

class TricycleDsAccessorImpl(private val dataStore: DataStore<TricycleDsEntity>) : TricycleDsAccessor {
    override fun getTricycleData(): Flow<TricycleDsEntity> = dataStore.data

    /**
     * Updates the data store with the new data
     * @param tricycleDsEntity the new data to be saved
     * Every null value in [TricycleDsEntity] will be ignored
     */
    override suspend fun updateTricycleData(tricycleDsEntity: TricycleDsEntity) {
        dataStore.updateData {
            it.copy(
                batteryPercentage = tricycleDsEntity.batteryPercentage ?: it.batteryPercentage,
                load = tricycleDsEntity.load ?: it.load,
                mileage = tricycleDsEntity.mileage ?: it.mileage,
                lastUpdated = tricycleDsEntity.lastUpdated ?: it.lastUpdated
            )
        }
    }
}