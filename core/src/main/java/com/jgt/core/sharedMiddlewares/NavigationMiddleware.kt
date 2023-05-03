package com.jgt.core.sharedMiddlewares

import com.jgt.core.mvi.Action
import com.jgt.core.mvi.Middleware
import com.jgt.core.mvi.Store
import com.jgt.core.sharedActions.NavigationActions
import com.jgt.core.sharedControllers.NavigationController

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 13/04/23.
 * * * * * * * * * * **/
data class NavigationMiddleware(
    override val store: Store
): Middleware {
    override fun next(action: Action): Action {
        if( action is NavigationActions) {
            val controller = NavigationController

            if (action is NavigationActions.NavigateToActivity)
                controller.navigateToActivity(
                    action.activityClassName,
                    mapOf( "path" to (action.composePath ?: "/") )
                )
        }

        return action
    }
}
