package com.jgt.wizelinebaz2023.core

import com.jgt.wizelinebaz2023.core.appmiddlewares.FirebaseAuthMiddleware
import com.jgt.wizelinebaz2023.core.appstate.UserState
import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.mvi.Store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
object AppStateStore: Store {
    override val middlewareList: List<Middleware> = listOf(
        FirebaseAuthMiddleware
    )

    private val _userState = MutableStateFlow<UserState>( UserState.Loading )
    val userState: StateFlow<UserState> = _userState

    override fun dispatch(action: Action): Action {
        var currentAction = action

        middlewareList.forEach {
            currentAction = it.next( currentAction )
        }

        _userState.value = _userState.value.reduce( currentAction )

        return currentAction
    }
}