package com.jgt.wizelinebaz2023.core.sharedStates

import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.sharedModels.User
import com.jgt.wizelinebaz2023.core.mvi.Caretaker
import com.jgt.wizelinebaz2023.core.mvi.ProductionRule
import com.jgt.wizelinebaz2023.core.mvi.State
import com.jgt.wizelinebaz2023.core.sharedActions.UserActions

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
                    is UserActions.ResultUserActions.SignedInAction       -> LoggedIn( action.user )
                    else -> LoggedOut
                }

                is LoggedIn ->
                    if( action is UserActions.ResultUserActions.LoggedOutAction )
                        LoggedOut
                    else
                        state

                LoggedOut -> when( action ) {
                    is UserActions.ResultUserActions.AccountCreatedAction -> LoggedIn( action.user )
                    is UserActions.ResultUserActions.SignedInAction       -> LoggedIn( action.user )
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
        TODO("Not yet implemented")
    }

    override fun <T : State> loadState(): T? {
        return UserState.LoggedOut as T
    }
}