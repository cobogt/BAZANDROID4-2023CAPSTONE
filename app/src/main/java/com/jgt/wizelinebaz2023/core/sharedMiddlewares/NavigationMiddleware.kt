package com.jgt.wizelinebaz2023.core.sharedMiddlewares

import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import com.jgt.wizelinebaz2023.core.sharedControllers.NavigationController

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 13/04/23.
 * * * * * * * * * * **/
object NavigationMiddleware: Middleware {
    override fun next(action: Action): Action {
        if( action is NavigationActions ) {
            val controller = NavigationController

            if (action is NavigationActions.NavigateToActivity)
                controller.navigateToActivity(
                    action.activityClassName,
                    mapOf( "path" to (action.composePath ?: "/") )
                )

                // is NavigationActions.NavigateToCompose -> controller.navigateToCompose( action.composePath )
        }

        return action
    }
}