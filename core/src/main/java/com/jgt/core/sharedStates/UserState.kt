package com.jgt.core.sharedStates

import com.jgt.core.mvi.Action
import com.jgt.core.mvi.Caretaker
import com.jgt.core.mvi.ProductionRule
import com.jgt.core.mvi.State
import com.jgt.core.sharedActions.UserActions
import com.jgt.core.sharedModels.User
import com.jgt.core.sharedStates.caretakers.UserStateCaretaker

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class UserState: State() {
    protected var sharedCaretaker: UserStateCaretaker = UserStateCaretaker()
    // (S)LoggedOut -> (A)LoginAction -> (A)ResultUserActions.LoggedInAction -> (S)LogeddIn
    object Loading: UserState() {
        override var caretaker: Caretaker? = sharedCaretaker
        override val productionRules = sharedProductionRules }
    object LoggedOut: UserState() {
        override var caretaker: Caretaker? = sharedCaretaker
        override val productionRules = sharedProductionRules }
    data class LoggedIn( val user: User): UserState() {
        override var caretaker: Caretaker? = sharedCaretaker
        override val productionRules = sharedProductionRules }

    override var caretaker: Caretaker? = sharedCaretaker

    protected val sharedProductionRules = listOf<ProductionRule> { state, action ->
        if( action is UserActions.ResultUserActions && state is UserState)
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
