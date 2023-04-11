package com.jgt.wizelinebaz2023.core.appactions

import com.jgt.wizelinebaz2023.core.appmodels.User
import com.jgt.wizelinebaz2023.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class UserActions: Action() {
    data class CreateAccountAction(
        val email:    String,
        val password: String
    ): UserActions()

    data class SignInAction(
        val email:    String,
        val password: String
    ): UserActions()

    object SendEmailVerificationAction: UserActions()
    object LogoutAction: UserActions()

    sealed class ResultUserActions: UserActions() {
        data class AccountCreatedAction( val user: User): ResultUserActions()
        data class SignedInAction( val user: User ):      ResultUserActions()
        object LoggedOutAction:                           ResultUserActions()
        object VerificationEmailSentAction:               ResultUserActions()
    }
}


