package com.jgt.wizelinebaz2023.core.sharedStates

import com.jgt.wizelinebaz2023.core.mvi.ProductionRule
import com.jgt.wizelinebaz2023.core.mvi.State
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 13/04/23.
 * * * * * * * * * * **/
sealed class NavigationState: State() {
    data class NavigateCompose( val path: String ): NavigationState() {
        override val productionRules: List<ProductionRule> = listOf {
            state, action ->
                if( action is NavigationActions.NavigateToCompose )
                    NavigateCompose( action.composePath )
                else
                    state
        }
    }
}
