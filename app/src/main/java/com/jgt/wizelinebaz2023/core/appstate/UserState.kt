package com.jgt.wizelinebaz2023.core.appstate

import com.jgt.wizelinebaz2023.core.appmodels.User
import com.jgt.wizelinebaz2023.core.mvi.State

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class UserState: State() {
    object Loading: UserState()
    object LoggedOut: UserState()
    data class LoggedIn( val user: User ): UserState()
}
