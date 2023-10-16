package com.example.core.data.local.datastore

//object PreferencesSerializer: Serializer<FilterPreferences> {
//    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()
//
//    @Suppress("BlockingMethodInNonBlockingContext")
//    override suspend fun readFrom(input: InputStream): UserPreferences {
//        try {
//            return UserPreferences.parseFrom(input)
//        } catch (exception: InvalidProtocolBufferException) {
//            throw CorruptionException("Cannot read proto.", exception)
//        }
//    }
//
//    @Suppress("BlockingMethodInNonBlockingContext")
//    override suspend fun writeTo(t: UserPreferences, output: OutputStream) = t.writeTo(output)
//}