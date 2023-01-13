package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TricycleDsAccessorImpl @Inject constructor(private val dataStore: DataStore<TricycleDsEntity>) : TricycleDsAccessor {
    override fun getTricycleData(): Flow<TricycleDsEntity> = dataStore.data

    override suspend fun updateTricycleData(tricycleDsEntity: TricycleDsEntity) {
        dataStore.updateData {
            it.copy(
                batteryPercentage = tricycleDsEntity.batteryPercentage ?: it.batteryPercentage,
                load = tricycleDsEntity.load ?: it.load,
                mileage = tricycleDsEntity.mileage ?: it.mileage,
                batteryTemperature = tricycleDsEntity.batteryTemperature ?: it.batteryTemperature,
                motorTemperature = tricycleDsEntity.motorTemperature ?: it.motorTemperature,
                lastUpdated = tricycleDsEntity.lastUpdated ?: it.lastUpdated
            )
        }
    }
}