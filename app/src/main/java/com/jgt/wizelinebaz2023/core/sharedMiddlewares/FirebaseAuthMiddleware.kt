package com.jgt.wizelinebaz2023.core.sharedMiddlewares

import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.sharedActions.UserActions
import com.jgt.wizelinebaz2023.core.sharedControllers.FirebaseAuthController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
object FirebaseAuthMiddleware: Middleware {
    override fun next( action: Action ): Action {
        if( action is UserActions ) {
            val controller = FirebaseAuthController
            return runBlocking {
                when( action ) {
                    is UserActions.CreateAccountAction ->
                        controller.createAccount( action.email, action.password ).first()

                    is UserActions.SignInAction ->
                        controller.signIn( action.email, action.password ).first()

                    UserActions.LogoutAction -> {
                        controller.signOut()

                        UserActions.ResultUserActions.LoggedOutAction
                    }

                    UserActions.SendEmailVerificationAction ->
                        controller.sendEmailVerification().first()

                    else -> action
                }
            }
        }

        return action
    }
}