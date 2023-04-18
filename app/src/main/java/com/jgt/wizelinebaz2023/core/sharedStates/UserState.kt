package com.jgt.wizelinebaz2023.core.sharedStates

import android.util.Log
import com.jgt.wizelinebaz2023.core.BaseApplication
import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.sharedModels.User
import com.jgt.wizelinebaz2023.core.mvi.Caretaker
import com.jgt.wizelinebaz2023.core.mvi.ProductionRule
import com.jgt.wizelinebaz2023.core.mvi.State
import com.jgt.wizelinebaz2023.core.sharedActions.UserActions
import com.jgt.wizelinebaz2023.storage.local.datastore.userStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class UserState: State() {
    object Loading: UserState() {
        override val productionRules = sharedProductionRules }
    object LoggedOut: UserState() {
        override val productionRules = sharedProductionRules }
    data class LoggedIn( val user: User ): UserState() {
        override val productionRules = sharedProductionRules }

    override val caretaker: Caretaker = UserStateCaretaker()

    protected val sharedProductionRules = listOf<ProductionRule> { state, action ->
        if( action is UserActions.ResultUserActions && state is UserState )
            when( state ) {
                Loading -> when( action ) {
                    is UserActions.ResultUserActions.AccountCreatedAction -> LoggedIn( action.user )
                    is UserActions.ResultUserActions.LoggedInAction       -> LoggedIn( action.user )
                    else -> LoggedOut
                }

                is LoggedIn ->
                    if( action is UserActions.ResultUserActions.LoggedOutAction )
                        LoggedOut
                    else
                        state

                LoggedOut -> when( action ) {
                    is UserActions.ResultUserActions.AccountCreatedAction -> LoggedIn( action.user )
                    is UserActions.ResultUserActions.LoggedInAction       -> LoggedIn( action.user )
                    else -> state
                }
            }
        else
            if( action is Action.ErrorAction )
                LoggedOut
            else
                state
    }
}

class UserStateCaretaker: Caretaker {
    override val defaultState: State = UserState.LoggedOut
    override fun <T : State> saveState(state: T) {
        Log.d("UserStateCaretaker", "SaveState $state")
        if( state is UserState ) {
            var email = ""
            var name  = ""
            var id    = ""
            val className = state.javaClass.simpleName

            when( state ) {
                is UserState.LoggedIn -> {
                    email = state.user.email
                    name  = state.user.name
                    id    = state.user.id
                }
                else -> {}
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

    override fun <T : State> loadState(): T? {
        Log.d("UserStateCaretaker", "LoadState")
        return runBlocking {
            BaseApplication.appContext.userStore.data.firstOrNull()?.let {
                Log.d("UserStateCaretaker", "LoadState $it")
                if( it.stateClass == "UserState.LoggedIn::class.simpleName")
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