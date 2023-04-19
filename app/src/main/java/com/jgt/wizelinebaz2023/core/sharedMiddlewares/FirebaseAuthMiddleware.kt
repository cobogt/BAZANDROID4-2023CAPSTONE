package com.jgt.wizelinebaz2023.core.sharedMiddlewares

import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.mvi.Store
import com.jgt.wizelinebaz2023.core.sharedActions.UserActions
import com.jgt.wizelinebaz2023.core.sharedControllers.FirebaseAuthController

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
data class FirebaseAuthMiddleware(
    override val store: Store
): Middleware {
    override fun next( action: Action ): Action {
        if( action is UserActions ) {
            val controller = FirebaseAuthController( store )

            when( action ) {
                is UserActions.CreateAccountAction -> {
                    controller.createAccount(action.email, action.password)
                    return UserActions.ResultUserActions.TryingLoginAction
                }

                is UserActions.LogInAction -> {
                    controller.signIn(action.email, action.password)
                    return UserActions.ResultUserActions.CreatingAccountAction
                }

                UserActions.LogoutAction -> {
                    controller.signOut()

                    UserActions.ResultUserActions.LoggedOutAction
                }

                UserActions.SendEmailVerificationAction ->
                    controller.sendEmailVerification()

                else -> { /* Nada por hacer */ }
            }

        }

        return action
    }
}