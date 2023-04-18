package com.jgt.wizelinebaz2023.storage.local.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 18/04/23.
 * * * * * * * * * * **/
object DatastoreSerializer: Serializer<UserStore> {
    override val defaultValue: UserStore
        get() = UserStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserStore {
        try {
            return UserStore.parseFrom( input )
        } catch (e: IOException) {
            throw CorruptionException("No se puede leer proto de UserStore", e)
        }
    }

    override suspend fun writeTo(t: UserStore, output: OutputStream) =
        t.writeTo( output )
}