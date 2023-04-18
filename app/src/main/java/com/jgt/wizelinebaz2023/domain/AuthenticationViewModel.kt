package com.jgt.wizelinebaz2023.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.jgt.wizelinebaz2023.core.AppStateStore
import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.mvi.Store
import com.jgt.wizelinebaz2023.presentation.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class AuthenticationViewModel: Store, ViewModel() {
    override val middlewareList: List<Middleware> = listOf()

    private val _loginComponentStateMutable = MutableStateFlow(LoginState.LoginData())
    val loginComponentState: StateFlow<LoginState> = _loginComponentStateMutable.asStateFlow()

    init {
        // Cargamos el estaado inicial de la vista
        dispatch( Action.LoadStateAction )
    }

    override fun dispatch(action: Action): Action {
        var currentAction = AppStateStore.dispatch( action )

        middlewareList.forEach { currentAction = it.next( currentAction ) }

        _loginComponentStateMutable.value = _loginComponentStateMutable.value.reduce( currentAction )

        return currentAction
    }
}