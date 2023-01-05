package com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
object TricycleProtoBufSerializer : Serializer<TricycleDsEntity> {
    override val defaultValue: TricycleDsEntity = TricycleDsEntity.empty()

    override suspend fun readFrom(input: InputStream): TricycleDsEntity {
        try {
            return ProtoBuf.decodeFromByteArray(
                TricycleDsEntity.serializer(), input.readBytes()
            )
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TricycleDsEntity, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                ProtoBuf.encodeToByteArray(TricycleDsEntity.serializer(), t)
            )
        }
    }
}