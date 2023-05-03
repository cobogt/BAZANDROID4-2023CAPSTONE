package com.jgt.content.movies.domain

import androidx.lifecycle.ViewModel
import com.jgt.core.AppStateStore
import com.jgt.core.mvi.Action
import com.jgt.core.mvi.Middleware
import com.jgt.core.mvi.Store
import com.jgt.core.sharedMiddlewares.FirebaseAuthMiddleware
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class AuthenticationViewModel: com.jgt.core.mvi.Store, ViewModel() {
    override val middlewareList: List<com.jgt.core.mvi.Middleware> = listOf(
        com.jgt.core.sharedMiddlewares.FirebaseAuthMiddleware(this)
    )

    private val currentActionMutable = MutableStateFlow<com.jgt.core.mvi.Action>(com.jgt.core.mvi.Action.VoidAction)
    override val currenAction: StateFlow<com.jgt.core.mvi.Action> = currentActionMutable.asStateFlow()


    override fun dispatch(action: com.jgt.core.mvi.Action): com.jgt.core.mvi.Action {
        var currentAction = com.jgt.core.AppStateStore.dispatch( action )

        middlewareList.forEach { currentAction = it.next( currentAction ) }

        currentActionMutable.value = currentAction

        return currentAction
    }
}
