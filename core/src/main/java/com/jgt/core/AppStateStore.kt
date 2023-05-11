package com.jgt.core

import com.jgt.core.gates.GatesMiddleware
import com.jgt.core.sharedStates.UserState
import com.jgt.core.mvi.Action
import com.jgt.core.mvi.Middleware
import com.jgt.core.mvi.Store
import com.jgt.core.sharedMiddlewares.NavigationMiddleware
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
object AppStateStore: Store {
    override val middlewareList: List<Middleware> = listOf(
        GatesMiddleware( this ),
        NavigationMiddleware( this ),
    )

    private val currentActionMutable = MutableStateFlow<Action>(Action.LoadStateAction)
    override val currenAction: StateFlow<Action> = currentActionMutable.asStateFlow()

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
