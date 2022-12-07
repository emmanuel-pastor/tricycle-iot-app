package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.`data-store`

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.emmanuel.pastor.simplesmartapps.tricycle.TricycleProtoEntity
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object TricycleProtoSerializer : Serializer<TricycleProtoEntity> {
    override val defaultValue: TricycleProtoEntity = TricycleProtoEntity.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TricycleProtoEntity {
        try {
            return TricycleProtoEntity.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TricycleProtoEntity, output: OutputStream) = t.writeTo(output)
}