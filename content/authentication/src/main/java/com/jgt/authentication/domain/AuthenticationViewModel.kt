package com.jgt.authentication.domain

import androidx.lifecycle.ViewModel
import com.jgt.core.AppStateStore
import com.jgt.core.mvi.Action
import com.jgt.core.mvi.Middleware
import com.jgt.core.mvi.Store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class AuthenticationViewModel: Store, ViewModel() {
    override val middlewareList: List<Middleware> = listOf(
        com.jgt.core.sharedMiddlewares.FirebaseAuthMiddleware(this)
    )

    private val currentActionMutable = MutableStateFlow<Action>(Action.VoidAction)
    override val currenAction: StateFlow<Action> = currentActionMutable.asStateFlow()


    override fun dispatch(action: Action): Action {
        var currentAction = AppStateStore.dispatch( action )

        middlewareList.forEach { currentAction = it.next( currentAction ) }

        currentActionMutable.value = currentAction

        return currentAction
    }
}
