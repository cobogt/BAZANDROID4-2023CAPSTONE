package com.jgt.core.sharedActions

import com.jgt.core.sharedModels.User
import com.jgt.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class UserActions: Action() {
    data class CreateAccountAction(
        val email:    String,
        val password: String
    ): UserActions()

    data class LogInAction(
        val email:    String,
        val password: String
    ): UserActions()

    object SendEmailVerificationAction: UserActions()
    object LogoutAction: UserActions()

    sealed class ResultUserActions: UserActions() {
        object CreatingAccountAction:                     ResultUserActions()
        object TryingLoginAction:                         ResultUserActions()
        data class AccountCreatedAction( val user: User): ResultUserActions()
        data class LoggedInAction(val user: User):       ResultUserActions()
        object LoggedOutAction:                           ResultUserActions()
        object VerificationEmailSentAction:               ResultUserActions()
    }
}


