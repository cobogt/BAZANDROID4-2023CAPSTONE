package com.jgt.wizelinebaz2023.presentation.actions

import com.jgt.wizelinebaz2023.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class LoginComponentActions: Action() {
    data class SetEmailAction( val newEmail: String ): LoginComponentActions()
    data class SetPasswordAction( val newPassword: String ): LoginComponentActions()
}
