package com.jgt.wizelinebaz2023.core

import com.jgt.wizelinebaz2023.core.sharedMiddlewares.FirebaseAuthMiddleware
import com.jgt.wizelinebaz2023.core.sharedStates.UserState
import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.mvi.Store
import com.jgt.wizelinebaz2023.core.sharedMiddlewares.NavigationMiddleware
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
object AppStateStore: Store {
    override val middlewareList: List<Middleware> = listOf(
        FirebaseAuthMiddleware,
        NavigationMiddleware,
    )

    private val userStateMutable = MutableStateFlow<UserState>( UserState.Loading )
    val userState: StateFlow<UserState> = userStateMutable

    private val currentPathComposeM =
        MutableStateFlow<Pair<String, Map<String, String>>>( Pair("/", mapOf()) )
    val currentPathCompose: StateFlow<Pair<String, Map<String, String>>> = currentPathComposeM

    override fun dispatch( action: Action ): Action {
        var currentAction = action

        middlewareList.forEach {
            currentAction = it.next( currentAction )
        }

        userStateMutable.value = userStateMutable.value.reduce( currentAction )

        return currentAction
    }
}