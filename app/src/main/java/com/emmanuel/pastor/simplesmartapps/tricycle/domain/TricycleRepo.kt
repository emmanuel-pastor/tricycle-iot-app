package com.emmanuel.pastor.simplesmartapps.tricycle.domain

import android.util.Log
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsAccessor
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.TricycleBleAccessor
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.mappers.BleTricycleDataMapper
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.models.TricycleData
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.models.toTricycleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TricycleRepo @Inject constructor(private val tricycleDsAccessor: TricycleDsAccessor, private val tricycleBleAccessor: TricycleBleAccessor) {
    fun getTricycleData(): Flow<TricycleData> {
        return tricycleDsAccessor.getTricycleData().catch { e ->
            Log.e("TricycleRepo", "Error getting tricycle data from data store", e)
        }.map { it.toTricycleData() }
    }

    suspend fun refreshTricycleData() {
        val batteryBleEntity = try {
            tricycleBleAccessor.getBatteryPercentage().getOrNull()
        } catch (e: Exception) {
            null
        }

        val loadBleEntity = try {
            tricycleBleAccessor.getLoad().getOrNull()
        } catch (e: Exception) {
            null
        }

        val mileageBleEntity = try {
            tricycleBleAccessor.getMileage().getOrNull()
        } catch (e: Exception) {
            null
        }

        val hasReceivedData = batteryBleEntity != null || loadBleEntity != null || mileageBleEntity != null
        val lastUpdated = if (hasReceivedData) System.currentTimeMillis() / 1000 else null

        BleTricycleDataMapper.toDsEntity(batteryBleEntity, loadBleEntity, mileageBleEntity, lastUpdated).also {
            tricycleDsAccessor.updateTricycleData(it)
        }
    }
}