package com.jgt.wizelinebaz2023.core.appstate

import com.jgt.wizelinebaz2023.core.appmodels.User
import com.jgt.wizelinebaz2023.core.mvi.Caretaker
import com.jgt.wizelinebaz2023.core.mvi.State

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class UserState: State() {
    object Loading: UserState()
    object LoggedOut: UserState()
    data class LoggedIn( val user: User ): UserState()

    override val caretaker: Caretaker = UserStateCaretaker()
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