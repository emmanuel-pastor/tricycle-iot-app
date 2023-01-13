package com.emmanuel.pastor.simplesmartapps.tricycle.domain.mappers

import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.*

object BleTricycleDataMapper {
    /**
     * @param lastUpdated the timestamp for when the most recent data was received in seconds
     */
    fun toDsEntity(
        batteryBleEntity: BatteryPercentageBleEntity?,
        loadBleEntity: LoadBleEntity?,
        mileageBleEntity: MileageBleEntity?,
        batteryTemperatureBleEntity: BatteryTemperatureBleEntity?,
        motorTemperatureBleEntity: MotorTemperatureBleEntity?,
        lastUpdated: Long?
    ): TricycleDsEntity {
        return TricycleDsEntity(
            batteryPercentage = batteryBleEntity?.percentage,
            load = loadBleEntity?.load,
            mileage = mileageBleEntity?.mileage,
            batteryTemperature = batteryTemperatureBleEntity?.temperature,
            motorTemperature = motorTemperatureBleEntity?.temperature,
            lastUpdated = lastUpdated
        )
    }
}