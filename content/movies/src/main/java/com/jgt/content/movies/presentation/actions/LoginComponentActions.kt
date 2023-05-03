package com.jgt.content.movies.presentation.actions

import com.jgt.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class LoginComponentActions: com.jgt.core.mvi.Action() {
    data class SetEmailAction( val newEmail: String ): LoginComponentActions()
    data class SetPasswordAction( val newPassword: String ): LoginComponentActions()
}
