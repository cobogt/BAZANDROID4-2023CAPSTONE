package com.jgt.authentication.gates

import android.util.Log
import com.jgt.core.AppStateStore
import com.jgt.core.gates.Gate
import com.jgt.core.mvi.Action
import com.jgt.core.mvi.navigationCatalog.NavigationCatalog
import com.jgt.core.sharedActions.NavigationActions
import com.jgt.core.sharedStates.UserState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 03/05/23.
 * * * * * * * * * * **/
class AuthGate: Gate() {
    override val startAction: Action =
        NavigationActions.NavigateToActivity(
            NavigationCatalog.AuthActivityTarget().className
        )

    override val onErrorAction: Action =
        Action.ErrorAction("Error al superar el Gate de autenticar")

    override fun enterCondition(action: Action): Boolean {
        return runBlocking {
            AppStateStore.userState.first() !is UserState.LoggedIn
        }
    }
}