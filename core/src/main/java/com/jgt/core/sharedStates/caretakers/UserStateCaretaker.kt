package com.jgt.core.sharedStates.caretakers

import android.util.Log
import com.jgt.core.mvi.Caretaker
import com.jgt.core.mvi.State
import com.jgt.core.sharedModels.User
import com.jgt.core.sharedStates.UserState
import com.jgt.core.BaseApplication
import com.jgt.core.storage.local.datastore.userStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 19/04/23.
 * * * * * * * * * * **/
class UserStateCaretaker: Caretaker {
    override val defaultState: State = UserState.LoggedOut
    override fun <T : State> saveState(state: T) {
        Log.d("UserStateCaretaker", "SaveState $state")
        if( state is UserState) {
            var email = ""
            var name  = ""
            var id    = ""
            val className = state.javaClass.simpleName

            if( state is UserState.LoggedIn ) {
                email = state.user.email
                name  = state.user.name
                id    = state.user.id
            }

            CoroutineScope( Dispatchers.IO ).launch {
                BaseApplication.appContext.userStore.updateData {
                    it.toBuilder()
                        .setEmail( email )
                        .setId( id )
                        .setName( name )
                        .setStateClass( className )
                        .build()
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : State> loadState(): T? {
        Log.d("UserStateCaretaker", "LoadState")
        return runBlocking {
            BaseApplication.appContext.userStore.data.firstOrNull()?.let {
                Log.d("UserStateCaretaker", "LoadState $it")
                if( it.stateClass == "LoggedIn")
                    UserState.LoggedIn(
                        User(
                            it.id,
                            it.name,
                            it.email,
                        )
                    )
                else
                    UserState.LoggedOut
            }
                ?: UserState.LoggedOut
        } as T
    }
}
