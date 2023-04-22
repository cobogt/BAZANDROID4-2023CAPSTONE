package com.jgt.wizelinebaz2023.storage.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 18/04/23.
 * * * * * * * * * * **/
val Context.userStore: DataStore<UserStore> by dataStore(
    fileName = "datastoreSerializer.pb",
    serializer = DatastoreSerializer
)
