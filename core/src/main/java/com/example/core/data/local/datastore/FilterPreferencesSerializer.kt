package com.example.core.data.local.datastore

import androidx.datastore.core.Serializer

object FilterPreferencesSerializer: Serializer<FilterPreferences> {
    override val defaultValue: FilterPreferences = FilterPreferences.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): FilterPreferences {
        try {
            return FilterPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: FilterPreferences, output: OutputStream) = t.writeTo(output)
}