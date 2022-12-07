package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalUnsignedTypes::class)
class BleEntitiesTest {
    @Test
    fun `create BatteryBleEntity from unsigned byte array`() = runBlocking {
        val uByteArray = ubyteArrayOf(0x50u)
        val batteryBleEntity = BatteryBleEntity.fromUByteArrayOrNull(uByteArray)
        assertEquals(80, batteryBleEntity?.percentage)
    }

    @Test
    fun `create LoadBleEntity from unsigned byte array`() = runBlocking {
        val uByteArray = ubyteArrayOf(0xFFu, 0x01u)
        val loadBleEntity = LoadBleEntity.fromUByteArrayOrNull(uByteArray)
        assertEquals(511, loadBleEntity?.loadWeight)
    }

    @Test
    fun `create MileageBleEntity from unsigned byte array`() = runBlocking {
        val uByteArray = ubyteArrayOf(0x80u, 0xCCu, 0x06u, 0x02u)
        val mileageBleEntity = MileageBleEntity.fromUByteArrayOrNull(uByteArray)
        assertEquals(34_000_000, mileageBleEntity?.mileage)
    }
}