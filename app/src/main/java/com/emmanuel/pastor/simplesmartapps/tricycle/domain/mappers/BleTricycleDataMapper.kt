package com.emmanuel.pastor.simplesmartapps.tricycle.domain.mappers

import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.BatteryBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.LoadBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.MileageBleEntity

object BleTricycleDataMapper {
    /**
     * @param lastUpdated the timestamp for when the most recent data was received in seconds
     */
    fun toDsEntity(
        batteryBleEntity: BatteryBleEntity?,
        loadBleEntity: LoadBleEntity?,
        mileageBleEntity: MileageBleEntity?,
        lastUpdated: Long?
    ): TricycleDsEntity {
        return TricycleDsEntity(
            batteryPercentage = batteryBleEntity?.percentage,
            load = loadBleEntity?.load,
            mileage = mileageBleEntity?.mileage,
            lastUpdated = lastUpdated
        )
    }
}