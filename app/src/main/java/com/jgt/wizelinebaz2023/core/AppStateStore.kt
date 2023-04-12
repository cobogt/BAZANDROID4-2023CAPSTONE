package com.jgt.wizelinebaz2023.core

import com.jgt.wizelinebaz2023.core.sharedMiddlewares.FirebaseAuthMiddleware
import com.jgt.wizelinebaz2023.core.sharedStates.UserState
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

    private val userStateMutable = MutableStateFlow<UserState>( UserState.Loading )
    val userState: StateFlow<UserState> = userStateMutable

    override fun dispatch( action: Action ): Action {
        var currentAction = action

        middlewareList.forEach {
            currentAction = it.next( currentAction )
        }

        userStateMutable.value = userStateMutable.value.reduce( currentAction )

        return currentAction
    }
}