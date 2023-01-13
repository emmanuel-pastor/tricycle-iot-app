package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store

import kotlinx.serialization.Serializable


@Serializable
data class TricycleDsEntity(
    val batteryPercentage: Int?,
    val load: Int?,
    val mileage: Int?,
    val batteryTemperature: Int?,
    val motorTemperature: Int?,
    val lastUpdated: Long?
) {
    companion object {
        fun empty() = TricycleDsEntity(null, null, null, null, null, null)
    }

    fun isEmpty() = this == empty()
}
