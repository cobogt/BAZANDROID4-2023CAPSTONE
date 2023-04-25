package com.jgt.wizelinebaz2023.core.sharedActions

import com.jgt.wizelinebaz2023.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 13/04/23.
 * * * * * * * * * * **/
sealed class NavigationActions: Action() {
    data class NavigateToActivity(
        val activityClassName: String,
        val composePath: String? = "",
    ): NavigationActions()

    data class NavigateToCompose(
        val composePath: String
    ): NavigationActions()

    object Back: NavigationActions()
}
